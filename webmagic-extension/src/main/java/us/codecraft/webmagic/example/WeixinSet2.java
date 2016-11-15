/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package us.codecraft.webmagic.example;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;


public class WeixinSet2  implements PageProcessor
{
  private String ppinf = null;
  private String pprdig = null;
  private String sig = null;

  private Site site = null;
  private String URL_POST;
  private String URL_LIST;
  private String regexContent;
  
  public void setCookies(String _ppinf, String _pprdig, String _sig)
  {
    this.ppinf = _ppinf;
    this.pprdig = _pprdig;
    this.sig = _sig;
    this.site = Site.me().setSleepTime(0).setRetryTimes(3)
      .addCookie(".sogou.com", "ppinf", this.ppinf)
      .addCookie(".sogou.com", "pprdig", this.pprdig)
      .addCookie("mp.weixin.qq.com", "sig", this.sig);
  }

  public void setURL_POST(String URL_POST)
  {
    this.URL_POST = URL_POST;
  }

  public void setURL_LIST(String URL_LIST) {
    this.URL_LIST = URL_LIST;
  }

  public void setRegexContent(String regex1) {
    this.regexContent = regex1;
  }


  public void process(Page page)
  {
    if (page.getUrl().regex("http://weixin.sogou.com/weixin.*").match())
    {
        
//      System.out.println("1");
//      System.out.println(page.getUrl());
//      System.out.println(page.getHtml().toString());
      List<String> profileList = page.getHtml().xpath("//a[@id='weixin_account']/outerHtml()").all();
      List<String> sList = page.getHtml().xpath("//div[@class='wx-rb bg-blue wx-rb_v1 _item']/outerHtml()").all();

      Pattern href = Pattern.compile("href=\"(.*?)\"");
      String accountReg = null;
      String sReg = null;
      for (String account : profileList)
      {
        Matcher matcher = href.matcher(account);
        matcher.find();
        accountReg = matcher.group(1).replaceAll("amp;", "");
        Request request = new Request(accountReg).putExtra("_level", Integer.valueOf(1));
        page.addTargetRequest(request);
      }
      for (String s : sList)
      {
        Matcher matcher = href.matcher(s);
        matcher.find();
        sReg = matcher.group(1).replaceAll("amp;", "");
        Request request = new Request(sReg).putExtra("_level", Integer.valueOf(1));
        page.addTargetRequest(request);
      }
      page.setSkip(true);
    }
    else if (page.getUrl().regex("http://mp.weixin.qq.com/profile.*").match())
    {
//      System.out.println("2");
//      System.out.println(page.getUrl());
//      System.out.println(page.getHtml().toString());
      String rawText = page.getRawText().replaceAll("&quot;", "").replaceAll("amp;", "");


      Pattern pattern_biz = Pattern.compile("var biz = \"(.*?)\"");
      Matcher matcher_biz = pattern_biz.matcher(rawText);
      matcher_biz.find();
      String biz = matcher_biz.group(1);


      String nickname = page.getHtml().xpath("//strong[@class='profile_nickname']/html()").toString();
      String accountName = page.getHtml().xpath("//p[@class='profile_account']/html()").toString();
      if (accountName != null)
      {
        accountName = accountName.substring(5);
      }


      String imgUrlRaw = page.getHtml().xpath("//span[@class='radius_avatar profile_avatar']/img/outerHtml()").toString();
      Pattern pattern_img = Pattern.compile("src=\"(.*?)\"");
      Matcher matcher_img = pattern_img.matcher(imgUrlRaw);
      matcher_img.find();
      String imgUrl = matcher_img.group(1);


      String description_1 = (String)page.getHtml().xpath("//div[@class='profile_desc_value']/html()").all().get(0);
      String description_2 = (String)page.getHtml().xpath("//div[@class='profile_desc_value']/html()").all().get(1);
      boolean qualified = false;
      if (description_2.startsWith("<img"))
      {
        qualified = true;
        description_2 = description_2.replaceAll("<[^>]*>", "");
      }

      Pattern pattern_content_url = Pattern.compile("content_url:(.*?),");
      Matcher matcher_content_url = pattern_content_url.matcher(rawText);
      matcher_content_url.find();
      String content_url = "http://mp.weixin.qq.com" + matcher_content_url.group(1).replaceAll("\\\\", "");
      Request request_getUrl = new Request(content_url)
        .putExtra("_level", Integer.valueOf(1))
        .putExtra("_biz", biz);
      page.addTargetRequest(request_getUrl);

      Pattern pattern_article = Pattern.compile("s?(timestamp=.*?),source_url");
      Pattern pattern_source_url = Pattern.compile("source_url:(.*?),");
      Pattern pattern_copyright_stat = Pattern.compile("copyright_stat:(.*?)}");


      Matcher matcher_article = pattern_article.matcher(rawText);
      Matcher matcher_source_url = pattern_source_url.matcher(rawText);
      Matcher matcher_copyright_stat = pattern_copyright_stat.matcher(rawText);
      while (matcher_article.find()) {
        matcher_source_url.find();
        matcher_copyright_stat.find();
        String requestUrl = "http://mp.weixin.qq.com/mp/getcomment?" + matcher_article.group(1);
//        String _source_url = page.getUrl().toString();
        String _source_url = matcher_source_url.group(1);
        String _copyright_stat = matcher_copyright_stat.group(1);
        Request request = new Request(requestUrl)
          .putExtra("_level", Integer.valueOf(1))
          .putExtra("_biz", biz)
          .putExtra("_imgUrl", imgUrl)
          .putExtra("_accountName", accountName)
          .putExtra("_nickname", nickname)
          .putExtra("_description_1", description_1)
          .putExtra("_description_2", description_2)
          .putExtra("_qualified", Boolean.valueOf(qualified))
          .putExtra("_source_url", _source_url)
          .putExtra("_copyright_stat", _copyright_stat);
        page.addTargetRequest(request);
      }
      page.setSkip(true);
    }
    else if (page.getUrl().regex("http://mp.weixin.qq.com/s.*").match())
    {
//      System.out.println("3");
//      System.out.println(page.getUrl());
//      System.out.println(page.getHtml().toString());
      String rawText = page.getRawText().replaceAll("&quot;", "").replaceAll("amp;", "");
      Pattern pattern_biz = Pattern.compile("msg_link = \"(.*?)\"");
      Matcher matcher = pattern_biz.matcher(rawText);
      matcher.find();
//      String url = matcher.group(1);
      String url = page.getUrl().toString();
      page.putField("biz", page.getRequest().getExtra("_biz"));
      page.putField("url", url);
    }
    else
    {
//      System.out.println("4");
//      System.out.println(page.getUrl());
//      System.out.println(page.getHtml().toString());
      page.putField("biz", page.getRequest().getExtra("_biz"));
      page.putField("imgUrl", page.getRequest().getExtra("_imgUrl"));
      page.putField("nickname", page.getRequest().getExtra("_nickname"));
      page.putField("accountName", page.getRequest().getExtra("_accountName"));
      page.putField("description_1", page.getRequest().getExtra("_description_1"));
      page.putField("description_2", page.getRequest().getExtra("_description_2"));
      page.putField("qualified", page.getRequest().getExtra("_qualified"));
      page.putField("source_url", page.getRequest().getExtra("_source_url"));
      page.putField("copyright_stat", page.getRequest().getExtra("_copyright_stat"));
      page.putField("elected_comment_total_cnt", new JsonPathSelector("$.elected_comment_total_cnt").select(page.getRawText()));
      page.putField("read_num", new JsonPathSelector("$.read_num").select(page.getRawText()));
      page.putField("like_num", new JsonPathSelector("$.like_num").select(page.getRawText()));
    }
  }


  public Site getSite()
  {
    return this.site;
  }
}
