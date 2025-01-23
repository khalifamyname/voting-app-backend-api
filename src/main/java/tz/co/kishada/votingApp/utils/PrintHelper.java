package tz.co.kishada.votingApp.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 11/13/24
 */


public class PrintHelper {

 private static final Logger log = LoggerFactory.getLogger(PrintHelper.class);


  private PrintHelper() throws Exception {
   throw new Exception("This is utility class, Dont instantiate");
  }

  public static void print(Object obj) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

      if (obj == null) {
      log.error("You've passed null object (from erp helper)");
    } else {

      String className = "";

      className = obj.getClass().getSimpleName();

      System.out.println("------------" + className + "---------------");
      try {
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
      } catch (JsonProcessingException e) {
        System.err.println("######################## ERROR from NLMIS Helper ########################");
        e.printStackTrace();
      }
      System.out.println("-------------------------------");
    }
  }

  public static void print(Object obj, String headerMsg) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


      if (obj == null) {
          log.error("You've passed null object (from erp helper)");
      } else {
            try {
                 System.out.println("");
                 System.out.println("");
                 String className;

                 className = obj.getClass().getSimpleName();

                 String msg = "--------------- " + className + " (" + headerMsg + ")  ----------------";

                 System.out.println(msg);

                 System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));

            } catch (Exception e) {
                 System.err.println("############ ERROR from IKMIS Helper ############");
                 e.printStackTrace();
            }

            System.out.println("---------------  End of " + headerMsg + " -------------------");
      }
  }

  public static String resourceMessage(Object id) {
   return " resource-id=" + id.toString();
  }

  public static String logging(String institutionId, String userIdentity, String msg) {
   return "#1 from #2 #3 ".replace("#1", userIdentity).replace("#2", institutionId).replace("#3", msg);
  }

  public static String accessMessage(String actionPastTense, Object entryId) {
   return " #1 a resource with id #2".replace("#1", actionPastTense).replace("#2", entryId.toString());
  }

  public static String accessMessageList(String actionPastTense, String extraInfo) {
   return " #1 a list resources (#2)".replace("#1", actionPastTense).replace("#2", extraInfo);
  }

  public static void printParams(Object... objects) {

   PrintHelper.print(objects, "List of parameters");

  }

  public static void printParams(String title, Object... objects) {

   PrintHelper.print(objects, title);

  }
}
