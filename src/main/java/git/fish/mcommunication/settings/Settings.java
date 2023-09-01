package git.fish.mcommunication.settings;

import java.io.File;

public class Settings extends Config {

    @Ignore
    public static final Settings IMP = new Settings();

    public String SERVER_PREFIX = "[SERVER]";
    public String GCHAT = "[G] %2$s";
    public String LCHAT = "%2$s";
    public String WRADIO = "[Слабая рация]";
    public String MRADIO = "[Продвинутая рация]";
    public String SRADIO = "[Мощная рация]";
    public int LOCALDIST = 10;
    public int COOLDOWN = 5;
    public String WMATERIAL = "OAK_BOAT";
    public String MMATERIAL = "APPLE";
    public String SMATERIAL = "POTATO";


    public void reload(File file) {
        if (this.load(file)) {
            this.save(file);
            return;
        }

        this.save(file);
        this.load(file);
    }
}