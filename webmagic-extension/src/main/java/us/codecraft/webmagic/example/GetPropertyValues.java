package us.codecraft.webmagic.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class GetPropertyValues
{
  String[] result = null;
  public String[] getPropValues() throws IOException
  {
      InputStream inputStream = null;
    try
    {
      Properties prop = new Properties();
      String propFileName = "crawlConfig.properties";

      inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

      if (inputStream != null)
      {
          prop.load(new InputStreamReader(inputStream, "UTF-8"));
      }
      else {
        throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath -zhu");
      }

      String channelIdList = prop.getProperty("channelIdList");
      String ppinf = prop.getProperty("ppinf");
      String pprdig = prop.getProperty("pprdig");
      String sig = prop.getProperty("sig");
      String tagFilePath = prop.getProperty("tagFilePath");
      String outputFilePath = prop.getProperty("outputFilePaht");

      this.result = new String[] { channelIdList, ppinf, pprdig, sig, tagFilePath, outputFilePath };
    } catch (Exception e) {
      System.out.println("Exception: " + e);
    } finally {
        inputStream.close();
    }
    return this.result;
  }
}
