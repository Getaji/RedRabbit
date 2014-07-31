package com.getaji.rrt.logic.config;

import com.getaji.rrt.model.SimpleToken;
import lombok.extern.log4j.Log4j2;
import org.ho.yaml.Yaml;
import org.ho.yaml.YamlDecoder;
import twitter4j.auth.AccessToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class DataSaveLogic {
    public static final File BASE_DIR;

    static {
        BASE_DIR = new File("./config/");
        if (!BASE_DIR.exists()) {
            boolean result = BASE_DIR.mkdir();
            if (result) log.info("Created config directory.");
        }
    }

    public static void saveToken(AccessToken accessToken, long id) throws IOException {
        SimpleToken simpleToken = new SimpleToken(accessToken);
        File file = new File(BASE_DIR, "account_" + id + ".yml");
        if (!file.exists()) {
            file.createNewFile();
        }
        Yaml.dump(simpleToken, file);
    }

    public static AccessToken[] loadTokens() {
        File[] files = BASE_DIR.listFiles((dir, name) -> {
            Matcher matcher = Pattern.compile("^account_\\d+.yml$").matcher(name);
            return matcher.find();
        });
        return (Arrays.stream(files).map(file -> {
            try {
                return loadToken(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }).toArray(AccessToken[]::new));
    }

    public static AccessToken loadToken(File file) throws FileNotFoundException {
        YamlDecoder decoder = new YamlDecoder(new FileInputStream(file));
        SimpleToken simpleToken = Yaml.loadType(file, SimpleToken.class);
        return simpleToken.create();
    }
}
