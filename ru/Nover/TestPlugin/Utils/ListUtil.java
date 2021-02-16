package ru.Nover.TestPlugin.Utils;

import ru.Nover.TestPlugin.JavaMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtil

{
    private ListUtil ( ) {}

    public static List<String> getList ( String string, int symbols, int linesCount ) {
        StringBuilder sb = new StringBuilder(string);

        {
            int i = 0;
            while ((i = sb.indexOf(" ", i + symbols)) != -1) {
                sb.replace(i, i + 1, "\n");
            }
        }

        List<String> response = new ArrayList<>();
        {
            String[] lines = sb.toString().split("\\n");

            StringBuilder currentPage = new StringBuilder();

            int i = 0;
            for (String line : lines) {
                if ( line.trim().isEmpty() )
                    continue;

                i++;

                if ( i % linesCount == 0 || i >= lines.length ) {
                    JavaMain.get().getLogger().info("Adding line: " + currentPage.toString() + ", Integer: " + (i % linesCount));

                    response.add(currentPage.toString());
                    currentPage.delete(0, currentPage.length());
                }

                currentPage.append(line).append("\n");
                JavaMain.get().getLogger().info("Line: " + line + ", Integer: " + (i%linesCount));
            }

            if ( !currentPage.toString().isEmpty() )
                response.add(currentPage.toString());
        }

        return response;
    }
}
