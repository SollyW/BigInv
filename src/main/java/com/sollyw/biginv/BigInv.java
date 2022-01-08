package com.sollyw.biginv;

import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BigInv {
    public static final String BIGINV = "biginv";
    public static final Logger LOGGER = LogManager.getLogger(BIGINV);

    public static Identifier id(String path) {
        return new Identifier(BIGINV, path);
    }
}
