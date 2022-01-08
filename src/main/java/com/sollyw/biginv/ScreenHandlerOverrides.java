package com.sollyw.biginv;

import com.sollyw.biginv.BigInvModInfo.RightmostBehaviour;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ScreenHandlerOverrides {
    public static final Map<Identifier, BigInvModInfo> SCREEN_HANDLER_OVERRIDES = new HashMap<>();

    private static boolean boolValue(String value) throws IllegalArgumentException {
        if ("true".equals(value)) return true;
        if ("false".equals(value)) return false;
        throw new IllegalArgumentException("\"" + value + "\" is not a boolean");
    }

    private static RightmostBehaviour rightmostBehaviourValue(String value) throws IllegalArgumentException {
        if ("extendFromRight".equals(value)) return RightmostBehaviour.EXTEND_FROM_RIGHT;
        if ("cap".equals(value)) return RightmostBehaviour.CAP;
        if ("none".equals(value)) return RightmostBehaviour.NONE;
        throw new IllegalArgumentException("\"" + value + "\" is invalid. Must be one of \"extendFromRight\"," +
                "\"cap\" or \"none\".");
    }

    private static IntOffset intOffsetValue(String value) throws IllegalArgumentException {
        if ("~".equals(value)) return IntOffset.NONE;

        boolean absolute = true;
        if (value.startsWith("~")) {
            absolute = false;
            value = value.substring(1);
        }

        return new IntOffset(absolute, Integer.parseInt(value));
    }

    public static void reload() {
        Path overridesPath = FabricLoader.getInstance().getConfigDir().resolve("biginv_overrides");
        try {
            if (Files.exists(overridesPath)) {
                SCREEN_HANDLER_OVERRIDES.clear();
                for (String line : Files.readAllLines(overridesPath)) {
                    try {
                        String[] fields = line.split(" ");

                        if (fields.length == 2) {
                            SCREEN_HANDLER_OVERRIDES.put(new Identifier(fields[0]), switch (fields[1]) {
                                default -> throw new IllegalArgumentException("Invalid override preset: " + fields[1]);
                                case "default" -> BigInvModInfo.DEFAULT;
                                case "generic_container" -> BigInvModInfo.GENERIC_CONTAINER;
                                case "player" -> BigInvModInfo.PLAYER;
                                case "merchant" -> BigInvModInfo.MERCHANT;
                                case "emulate" -> BigInvModInfo.EMULATE;
                            });
                            continue;
                        }

                        if (fields.length == 18) {
                            SCREEN_HANDLER_OVERRIDES.put(new Identifier(fields[0]), new BigInvModInfo(
                                    boolValue(fields[1]),
                                    boolValue(fields[2]),
                                    boolValue(fields[3]),
                                    boolValue(fields[4]),
                                    intOffsetValue(fields[5]),
                                    intOffsetValue(fields[6]),
                                    intOffsetValue(fields[7]),
                                    intOffsetValue(fields[8]),
                                    boolValue(fields[9]),
                                    intOffsetValue(fields[10]),
                                    intOffsetValue(fields[11]),
                                    boolValue(fields[12]),
                                    rightmostBehaviourValue(fields[13]),
                                    intOffsetValue(fields[14]),
                                    intOffsetValue(fields[15]),
                                    intOffsetValue(fields[16]),
                                    intOffsetValue(fields[17])));
                            continue;
                        }

                        throw new IllegalArgumentException("Wrong number of arguments. Expected 2 or 18, got "
                                + fields.length);
                    } catch (IllegalArgumentException | InvalidIdentifierException e) {
                        BigInv.LOGGER.warn("Failed to add override \"" + line + "\": " + e.getMessage());
                    }
                }

            } else {
                Files.createFile(overridesPath);
            }
        } catch (IOException e) {
            BigInv.LOGGER.error("Failed to access overrides file");
            e.printStackTrace();
        }
    }

    static {
        reload();
    }
}
