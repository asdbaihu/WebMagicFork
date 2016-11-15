//package us.codecraft.webmagic.example;
//
//import us.codecraft.webmagic.Page;
//import us.codecraft.webmagic.Request;
//import us.codecraft.webmagic.Site;
//import us.codecraft.webmagic.processor.PageProcessor;
//
//import java.util.Date;
//
//import org.apache.commons.lang.StringUtils;
//
//public class SampleCrawlerSetBak implements PageProcessor {
//
//    private Site site = Site.me().setSleepTime(0).setRetryTimes(3);
//    
//    public String URL_LIST;
//    public String URL_POST;
//    public String regex1;
//    public String regex2;
//    public String regex3;
//    public String regex4;
//    public String regex5;
//    
//    public void setURL_POST(String URL_POST) {
//        this.URL_POST = URL_POST;
//    }
//    
//    public void setURL_LIST(String URL_LIST) {
//        this.URL_LIST = URL_LIST;
//    }
//    
//	public void setRegex1(String regex1) {
//		this.regex1 = regex1;
//	}
//
//	public void setRegex2(String regex2) {
//		this.regex2 = regex2;
//	}
//
//	public void setRegex3(String regex3) {
//		this.regex3 = regex3;
//	}
//	
//    public void setRegex4(String regex4) {
//        this.regex4 = regex4;
//    }
//
//    public void setRegex5(String regex5) {
//        this.regex5 = regex5;
//    }
//    
//	@Override
//    public void process(Page page) {
////		Request request = new Request(link).setPriority(0).putExtra("province", title);
////		Date date = new Date(); 
////		System.out.println(_level);
////        page.addTargetRequests(page.getHtml().links().regex(URL_POST).all(), _level);
////        page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all(), _level);
//        
//	    if(page.getUrl().regex(URL_POST).match())
//	    {
//	        page.putField("title", page.getHtml().xpath(regex1).toString().replaceAll("<[^>]*>",""));
//	        if(page.getResultItems().get("title")==null)
//	        {
//	        	page.setSkip(true);
//	        }
//	        page.putField("content", StringUtils.join(page.getHtml().xpath(regex2).all(), "\n"));//.replaceAll("<[^>]*>",""));    //匹配种子smartContent()
//	        page.putField("mpMediaId-passport", regex3);
//	        page.putField("gatherTime", new Date().toString());
//	        page.putField("postTime", page.getHtml().xpath(regex4).toString());
////	        try {
////                page.putField("postTime", new SimpleDateFormat().parse(page.getHtml().xpath(regex4).toString()));
////            } catch (ParseException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            }
//	        page.putField("channelId", regex5);
//	        
////	        page.putField("date", page.getHtml().xpath(regex2).toString());
////	        page.putField("keywords", page.getHtml().xpath(regex4).all().toString());
////	        page.putField("source", page.getUrl().toString());
//	    }
//	    else{
//	    	page.setSkip(true);
//	    }
//    }
//
//    @Override
//    public Site getSite() {
//        return site;
//    }
//
//  }