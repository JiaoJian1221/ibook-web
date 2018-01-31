package me.jiaojian.controller;

import me.jiaojian.domain.*;
import me.jiaojian.repository.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by jiaojian on 2017/12/29.
 */
@RestController
@RequestMapping("/jd")
public class JdController {

  @Autowired
  private ChannelRepository channelRepository;

  @Autowired
  private CatalogRepository catalogRepository;

  @Autowired
  private ImgRepository imgRepository;

  @Autowired
  private ImgUsageRepository imgUsageRepository;

  @Autowired
  private BookRepository bookRepository;


  @GetMapping("/channels")
  public void getChannels() throws IOException {
    Document doc = Jsoup.connect("http://book.jd.com/booksort.html").get();
    doc.select("div#booksort div.mc dl dt").stream().forEach(dt ->{
      final Channel channel = saveChannel(dt.select("a").first());
      dt.nextElementSibling().select("em a").forEach(e -> {
        saveCatalog(channel, e.select("a").first());
      });
    });
  }

  @GetMapping("/channels/all/banners")
  public void getBanners() throws IOException {
    for(Channel channel : channelRepository.findAll()) {
      getBanners(channel.getId());
    }
  }

  private File IMG_DIR = new File("/Users/jiaojian/Downloads/imgs");

  @GetMapping("/channels/{id}/banners")
  public void getBanners(@PathVariable Long id) throws IOException {
    Channel channel = channelRepository.findOne(id);
    Document doc = Jsoup.connect(channel.getJdUri()).get();
    doc.select("div#p-slider div.slider-wrap ul li div.i-wrap div.i-inner a").stream().forEach(a -> {
      String src = a.select("img").first().attr("abs:data-src");
      Img img = saveImg(src, new File(IMG_DIR, ImgUsage.Type.CHANNEL_BANNER_IMG.name().toLowerCase()));
      ImgUsage usage = imgUsageRepository.findByImgAndTypeAndObjId(img, ImgUsage.Type.CHANNEL_BANNER_IMG, channel.getId());
      if(usage == null) {
        usage = new ImgUsage();
        usage.setImg(img);
        usage.setType(ImgUsage.Type.CHANNEL_BANNER_IMG);
        usage.setObjId(channel.getId());
      }
      imgUsageRepository.save(usage);
    });

  }


  @GetMapping("/catalogs/{id}/books")
  public void getBooks(@PathVariable Long id) throws IOException {
    Catalog catalog = catalogRepository.findOne(id);
    Document doc = Jsoup.connect(catalog.getJdUri()).get();
    doc.select("div#plist ul li div.j-sku-item").stream().forEach(div -> {
      Book book = saveBook(div, catalog);

      String src = div.select("div.p-img a img").first().attr("abs:src");
      Img img = saveImg(src, new File(IMG_DIR, ImgUsage.Type.BOOK_THUMB_IMG.name().toLowerCase()));

      ImgUsage usage = imgUsageRepository.findByImgAndTypeAndObjId(img, ImgUsage.Type.BOOK_THUMB_IMG, book.getId());
      if(usage == null) {
        usage = new ImgUsage();
        usage.setImg(img);
        usage.setType(ImgUsage.Type.BOOK_THUMB_IMG);
        usage.setObjId(book.getId());
      }
      imgUsageRepository.save(usage);

    });

  }

  @GetMapping("/catalogs/all/books")
  public void getBooks() throws IOException {
    for(Catalog catalog : catalogRepository.findAll()) {
      getBooks(catalog.getId());
    }
  }

  private Book saveBook(Element div, Catalog catalog) {
    String href = div.select("div.p-img a").first().attr("abs:href");
    Book book = bookRepository.findByJdUri(href);
    if(book == null) {
      book = new Book();
      book.setJdUri(href);
    }
    book.setJdSku(div.attr("data-sku"));
    book.setName(div.select("div.p-name a em").first().text().trim());
    if(div.select("div.p-bookdetails span.p-bi-name span.author_type_1 a").first() != null) {
      book.setAuthor(div.select("div.p-bookdetails span.p-bi-name span.author_type_1 a").first().text().trim());
    }
    if(div.select("div.p-bookdetails span.p-bi-store a").first() != null) {
      book.setPublisher(div.select("div.p-bookdetails span.p-bi-store a").first().text().trim());
    }
    if(div.select("div.p-bookdetails span.p-bi-date").first() != null) {
      book.setPublishDate(div.select("div.p-bookdetails span.p-bi-date").first().text().trim());
    }
    book.setCatalog(catalog);
    return bookRepository.save(book);
  }

  private Img saveImg(String src, File dir) {
    Img i = imgRepository.findByJdUri(src);
    if(i == null) {
      i = new Img();
      i.setJdUri(src);
    }
    i.setName(FilenameUtils.getName(src));
    copyImage(i, dir);
    return imgRepository.save(i);
  }

  private void copyImage(Img img, File dir) {
    try {
      File file = new File(dir, img.getName());
      if(!file.exists()) {
        FileUtils.copyURLToFile(new URL(img.getJdUri()), file);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Catalog saveCatalog(Channel channel, Element e) {
    String href = e.attr("abs:href");
    Catalog catalog = catalogRepository.findByJdUri(href);
    if(catalog == null) {
      catalog = new Catalog();
      catalog.setJdUri(href);
    }
    catalog.setChannel(channel);
    catalog.setName(e.text());
    return catalogRepository.save(catalog);
  }

  private Channel saveChannel(Element e) {
    String href = e.attr("abs:href");
    Channel channel = channelRepository.findByJdUri(href);
    if(channel == null) {
      channel = new Channel();
      channel.setJdUri(href);
    }
    channel.setName(e.text());
    return channelRepository.save(channel);
  }
}
