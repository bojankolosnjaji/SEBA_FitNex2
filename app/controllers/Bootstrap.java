package controllers;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;


import play.*;
import play.jobs.*;
import play.test.*;
 
import models.*;
 
@OnApplicationStart
public class Bootstrap extends Job {

	@SuppressWarnings("deprecation")
	public void doJob() {

		File file = new File("logs");
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return new File(dir, name).isDirectory();
			}
		});
		
		if (directories != null) {
			DateTime dt1 = DateTime.now();
			for (String dirName : directories) {

				String[] strs = dirName.split("_");

				DateTime dt2 = new DateTime(Integer.valueOf(strs[2]),
						Integer.valueOf(strs[1]), Integer.valueOf(strs[0]), 0,
						0, 0, 0);
				int days = Days.daysBetween(dt1, dt2).getDays();
				if (days > LogMaker.maxAllowedLogDays) {
					try {
						delete(new File(dirName));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

    }
    
    void delete(File f) throws IOException {
    	  if (f.isDirectory()) {
    	    for (File c : f.listFiles())
    	      delete(c);
    	  }
    	  if (!f.delete())
    	    throw new FileNotFoundException("Failed to delete file: " + f);
    	}
 
}