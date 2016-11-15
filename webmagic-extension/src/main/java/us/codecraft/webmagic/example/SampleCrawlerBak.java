//package us.codecraft.webmagic.example;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import us.codecraft.webmagic.Spider;
//import us.codecraft.webmagic.scheduler.QueueScheduler;
//
//public class SampleCrawlerBak {
//   
//    public List<String> doCrawl(String channelId)
//    {
//        List<String> HotWordMapList = new ArrayList<String>();
//        try {
//            File logfile =  getModelFile(channelId);
//            
//            BufferedReader reader = new BufferedReader(new FileReader(logfile));
//            reader.readLine();
//            String line = null; 
//            while((line=reader.readLine())!=null){ 
//                String item[] = line.split(",");
//                SampleCrawlerSet fashionSet = new SampleCrawlerSet();
//                fashionSet.setURL_POST(item[1]);
//                fashionSet.setURL_LIST(item[2]);
//                fashionSet.setRegex1(item[3]);//title
//                fashionSet.setRegex2(item[4]);//content
//                fashionSet.setRegex3(item[5]);//id-passport
////                fashionSet.setRegex3(item[5]);//gatherTime
//                fashionSet.setRegex4(item[6]);//postTime
//                fashionSet.setRegex5(item[7]);//channelID
////                System.out.println(item[0].replaceAll("/", "-"));
//                
//                Spider spider = spider.create(SampleCrawlerSet)
//                   .addUrl(item[0])
//                   .scheduler(new QueueScheduler())
////                   .addPipeline(new ConsolePipeline())
////                   .addPipeline(new JsonFilePipeline("D:\\mpData\\rawData\\" + channelId.replace("\"", "")))
//                   .thread(1);
//
//                spider.run();
//                HotWordMapList.addAll(spider.outputTitleList);
//                //回收
//            }
//            reader.close();
//        } catch (Exception e) { 
//            e.printStackTrace(); 
//        } 
//        return HotWordMapList;
//    }
//    
//    private File getModelFile(String channelId) throws IOException {
//        String CSV_PATH = "./website" + channelId + ".csv";
//        org.springframework.core.io.Resource resource = new ClassPathResource(CSV_PATH);
//        if (resource.exists()) {
//            return resource.getFile();
//        }
//        return null;
//    }
//}
