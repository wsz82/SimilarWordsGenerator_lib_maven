package io.github.wsz82;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class LoaderWords extends ALoader{
    @Override
    Analyser load (String path) {
        List<String> loadList = new ArrayList<>();
        Analyser analyser = new Analyser();

        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))
                ){
            String temp;

            br.mark(3);
            if (br.read() != '\ufeff') br.reset();
            while ( (temp = br.readLine()) != null ) {
                if (!temp.equals("")) loadList.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        analyser.analyze(loadList);
        return analyser;
    }
}
