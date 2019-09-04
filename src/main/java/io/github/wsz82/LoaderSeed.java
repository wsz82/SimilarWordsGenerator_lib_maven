package io.github.wsz82;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

class LoaderSeed extends ALoader {
    @Override
    Analyser load(String path) {
        Analyser analyser = new Analyser();

        try (
                ObjectInputStream os = new ObjectInputStream(new FileInputStream(path))
                ) {
            analyser = (Analyser) os.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return analyser;
    }
}
