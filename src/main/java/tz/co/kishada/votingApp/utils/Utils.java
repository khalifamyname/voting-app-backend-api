/**
 * 
 */
package tz.co.kishada.votingApp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


@Component
public class Utils {

	public static String replaceCharacter(String originalString, String charToReplace, String replacementChar) {
		if (originalString.indexOf(charToReplace) != -1) {
			return originalString.replace(charToReplace, replacementChar);
		} else {
			return originalString;
		}
	}

	public static String replaceAndCapitalize(String originalString, String charToReplace, String replacementChar) {
		// Replace the character
		String replacedString = originalString.replace(charToReplace, replacementChar);

		// Split the string into words
		String[] words = replacedString.split(replacementChar);

		// Capitalize each word
		StringBuilder capitalizedString = new StringBuilder();
		for (String word : words) {
			if (!word.isEmpty()) {
				capitalizedString.append(Character.toUpperCase(word.charAt(0)))  // Capitalize first letter
						.append(word.substring(1).toLowerCase())        // Convert the rest to lowercase
						.append(" ");                                   // Add a space
			}
		}

		// Return the final string, trimmed of trailing spaces
		return capitalizedString.toString().trim();
	}

	public static String getDayOfWeek(LocalDate date) {
		// Get the day of the week using the built-in formatter
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH);
		return date.format(formatter);
	}
	

	/**
     * Copies properties from one object to another
     * @param source
     * @destination
     * @return
     */
    public static void copyNonNullProperties(Object source, Object destination){
		BeanUtils.copyProperties(source, destination,
				getNullPropertyNames(source));
    }
    /**
     * Returns an array of null properties of an object
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
 
	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
		//check if value of this property is null then add it to the collection
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }
	    String[] result = new String[emptyNames.size()];
	    return (String[]) emptyNames.toArray(result);
	}


	public static String getFileExtensionFromString(String filename) {
		if (filename == null || filename.isEmpty()) {
			return "";
		}
		int dotIndex = filename.lastIndexOf(".");
		if (dotIndex >= 0 && dotIndex < filename.length() - 1) {
			return filename.substring(dotIndex + 1);
		} else {
			return "";
		}
	}

	public static void print(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String className = "";
		if (obj == null) {
			className = "You've passed null object";
		} else {
			className = obj.getClass().getSimpleName();
		}
		System.out.println("--------------------------" + className
				+ "----------------------------------------------------------");
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			System.out.println("########################ERROR########################");
			e.printStackTrace();
		}
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");
	}
    
    public static boolean isNumeric(final String str) {

        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;

    }

    public static String harshMethod(String string) throws NoSuchAlgorithmException  {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(string.getBytes());

        byte[] byteData = md.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
        }
        return sb.toString();
    }

	public static String writeBase64File(String base64File, String uploadPath, String joinString, String nationalId)
			throws NoSuchAlgorithmException, IOException {
		String extension = ".pdf";
		String fileName = "";
		String pathDelimiter = "/";

		Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
		String tempFileName = nationalId + joinString + ts;
		fileName = harshMethod(tempFileName);

		String fullPath = uploadPath + pathDelimiter + fileName + extension;
		Path path = Paths.get(fullPath);
		byte[] bytes = Base64.getDecoder().decode(base64File);
		Files.write(path, bytes);

		return fullPath;
	}
	
	public static String writePhotoFile(String base64File, String uploadPath, String joinString, String nationalId)
			throws NoSuchAlgorithmException, IOException {
		String extension = ".png";
		String fileName = "";
		String pathDelimiter = "/";

		Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
		String tempFileName = nationalId + joinString + ts;
		fileName = harshMethod(tempFileName);

		String fullPath = uploadPath + pathDelimiter + fileName + extension;
		Path path = Paths.get(fullPath);
		byte[] bytes = Base64.getDecoder().decode(base64File);
		Files.write(path, bytes);

		return fullPath;
	}

    public static void createDirectoryIfDoesntExist(String uploadDir) {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }


}
