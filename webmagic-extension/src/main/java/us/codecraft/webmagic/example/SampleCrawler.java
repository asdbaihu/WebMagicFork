package us.codecraft.webmagic.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class SampleCrawler {
   
    public void doCrawl(List<String> settings)
    {

        SampleCrawlerSet sampleCrawlerSet = new SampleCrawlerSet();
        sampleCrawlerSet.URL_POST = settings.get(2);
        sampleCrawlerSet.URL_LIST = settings.get(3);
        sampleCrawlerSet.post_piece = settings.get(4);
        sampleCrawlerSet.regex_link = settings.get(5);
        sampleCrawlerSet.regex_link = settings.get(6);
        sampleCrawlerSet.regexTitle = settings.get(7);
        sampleCrawlerSet.regexContent = settings.get(8);
        
        int num = 100;
        String[] urlSeedList_content = new String[num];
        for (int pageIndex = 1; pageIndex < num + 1; ++pageIndex)
        {
          urlSeedList_content[(pageIndex - 1)] = settings.get(0) + pageIndex + settings.get(1);
        }
        
        Spider newSpider = Spider.create(sampleCrawlerSet)
          .addUrl(urlSeedList_content)
          .scheduler(new QueueScheduler())
          .addPipeline(new JsonFilePipeline(settings.get(5)))
          .thread(12);
        newSpider.run();
    }

    public static void main(String[] args)
    {
        SampleCrawler sampleCrawler = new SampleCrawler();
        List<List<String>> settingsList = sampleCrawler.getWebsiteSetting();
        for (List<String> settings : settingsList)
        {
            sampleCrawler.doCrawl(settings);
        }
        
    }
    private List<List<String>> getWebsiteSetting()
    {
        List<List<String>> settings = new ArrayList<List<String>>();
        settings.add(Arrays.asList("http://www.esquire.com.cn/c/featurelab?page=",
                "",
                "http://www.esquire.com.cn/posts/.*",//2 post
                "http://www.esquire.com.cn/c/featurelab?page=.*",//3 list
                "http://www.esquire.com.cn/",//4 post piece
                "//a[@class='col-xs-6 cover-box']/outerHtml()",//5 regex_link
                "href=\"(.*?)\"",//6 regex_href
                "//div[@class='post-title']/html()",//7 title
                "//div[@class='show-content']/html()",//8 content
                "esquire"
                ));
        return settings;
    }
}
