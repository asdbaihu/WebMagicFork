package us.codecraft.webmagic.example;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;


public class SampleCrawlerSet  implements PageProcessor
{
  private Site site = Site.me().setSleepTime(0).setRetryTimes(3);
  
  public String URL_POST = null;
  public String URL_LIST = null;
  public String post_piece = null;
  public String regex_link = null;
  public String regex_href = null;
  public String regexTitle = null;
  public String regexContent = null;

  public void process(Page page)
  {
    if (page.getUrl().regex(URL_POST).match())
    {
      page.putField("title", page.getHtml().xpath(regexTitle).toString().replaceAll("<[^>]*>",""));
      page.putField("content", StringUtils.join(page.getHtml().xpath(regexContent).all(), "\n"));//.replaceAll("<[^>]*>",""));    //匹配种子smartContent()
      if (page.getResultItems().get("title") == null)
      {
    	  page.setSkip(true);
      }
    }
    
    
    else if (page.getUrl().regex(URL_LIST).match())
    {
//        String rawText = page.getRawText();
        String rawText = page.getRawText().replaceAll("\\\\", ""); //fucking remember
        
        Pattern href = Pattern.compile(regex_href);
        String fullLink = null;
        Matcher matcher = href.matcher(rawText);
        while(matcher.find())
        {
          fullLink = matcher.group(1);
          Request request = new Request(fullLink);
          page.addTargetRequest(request);
        }
        page.setSkip(true);
    }
//    else if (page.getUrl().regex(URL_LIST).match())
//    {
//    	String linkList = page.getRawText().replaceAll("\\", "");
////        List<String> linkList = page.getHtml().xpath(regex_link).all();
//    	
//    	Pattern href = Pattern.compile(regex_href);
//    	String fullLink = null;
////        for (String link : linkList)
////        {
////          Matcher matcher = href.matcher(link);
////          matcher.find();
////          link = matcher.group(1);
////          fullLink = link;
//////          fullLink = post_piece + link;
////          Request request = new Request(fullLink);
////          page.addTargetRequest(request);
////        }
//    	page.setSkip(true);
//    }
    
    else
    {
        page.setSkip(true);
    }
  }


    @Override
    public Site getSite() {
        return site;
    }
}
