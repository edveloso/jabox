package org.jabox.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Unzip {
    private static final Logger LOGGER = LoggerFactory
        .getLogger(Unzip.class);

    public static void unzip(final File zipFile, final File baseDir)
            throws IOException {
        InputStream in = null;
        ZipInputStream zin = null;
        try {
            in = new BufferedInputStream(new FileInputStream(zipFile));
            zin = new ZipInputStream(in);
            ZipEntry e;

            while ((e = zin.getNextEntry()) != null) {
                if (!e.isDirectory()) {
                    unzip(zin, baseDir, e.getName());
                }
            }
        } finally {
            if (zin != null)
              zin.close();
            if (in != null)
              in.close();
        }

    }

    public static void unzip(final ZipInputStream zin, final File baseDir,
            final String entry) throws IOException {
        File file = new File(baseDir, entry);
        LOGGER.info("unzipping " + file.getAbsolutePath());
        file.getParentFile().mkdirs();
        file.setExecutable(true);
        FileOutputStream out = new FileOutputStream(file);
        byte[] b = new byte[512];
        int len = 0;
        while ((len = zin.read(b)) != -1) {
            out.write(b, 0, len);
        }
        out.close();
    }
}
