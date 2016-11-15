package us.codecraft.webmagic.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class WeixinCrawler2
{
  public void doCrawl(String keyWord, int fileIndex, String tag, String[] config)
  {
    int num = 100;
    String[] urlSeedList_content = new String[num];
    String[] urlSeedList_name = new String[num];
    for (int pageIndex = 1; pageIndex < num + 1; ++pageIndex)
    {
      urlSeedList_content[(pageIndex - 1)] = "http://weixin.sogou.com/weixin?query=" + keyWord + "&_sug_type_=&_sug_=n&type=2&page=" + pageIndex + "&ie=utf8";
      urlSeedList_name[(pageIndex - 1)] = "http://weixin.sogou.com/weixin?query=" + keyWord + "&_sug_type_=&_sug_=n&type=1&page=" + pageIndex + "&ie=utf8";

    }
    WeixinSet2 weixinSet2 = new WeixinSet2();
    weixinSet2.setCookies(config[1], config[2], config[3]);
    weixinSet2.setURL_POST("http://mp.weixin.qq.com/profile.*");



    weixinSet2.setRegexContent("//div[@class='rich_media_content ']//p/allText()");
    Spider newSpider = Spider.create(weixinSet2)
      .addUrl(urlSeedList_content)
      .addUrl(urlSeedList_name)
      .scheduler(new QueueScheduler())
      .addPipeline(new JsonFilePipeline(config[5] + tag + '_' + fileIndex))
      .thread(12);
    newSpider.run();
  }

  public static void main(String[] args) throws IOException {
    GetPropertyValues properties = new GetPropertyValues();
    String[] config = properties.getPropValues();
    String[] tagsList = config[0].split(",");
    Map<String, String> channelMap = new HashMap<String, String>();
    channelMap.put("12","文化");
    channelMap.put("13","历史");
    channelMap.put("15","财经");
    channelMap.put("17","体育");
    channelMap.put("18","汽车");
    channelMap.put("19","娱乐");
    channelMap.put("23","时尚");
    channelMap.put("25","教育");
    channelMap.put("27","星座");
    channelMap.put("30","科技");
    channelMap.put("39","+");
    channelMap.put("41","动漫");
    channelMap.put("42","游戏");
    channelMap.put("44","宠物");
    channelMap.put("45","搞笑");
    for (String tag : tagsList)
    {
      String filepath = config[4] + String.format("%s.txt", new Object[] { tag });
      BufferedReader tagsFile = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),  "UTF-8"));
//      BufferedReader tagsFile = new BufferedReader(new FileReader(filepath));
//      System.out.println(tag);
      int index = 0;      String line;
      while ((line = tagsFile.readLine()) != null)
      {
//        System.out.println(line);
        WeixinCrawler2 ft = new WeixinCrawler2();
        System.out.println(channelMap.get(tag) + line);
        ft.doCrawl(channelMap.get(tag) + line, index, tag, config);
        ++index;
//        System.out.println("******************************************************");
//        System.out.println("******************************************************");
      }
      tagsFile.close();
    }
  }
}
