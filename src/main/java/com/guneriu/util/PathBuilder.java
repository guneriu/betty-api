package com.guneriu.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ugur on 14.05.2016.
 */
public class PathBuilder {

    public static String SEPERATOR = "/";

    public static String build(String... paths) {
        List<String> pathParameters = Arrays.asList(paths);

        if (pathParameters.isEmpty()) {
            return "";
        }

        if (pathParameters.size() == 1) {
            return pathParameters.get(0);
        }

        String path = "";
        for(String param: pathParameters) {
            path += param + SEPERATOR;
        }

        return path;
    }

}

