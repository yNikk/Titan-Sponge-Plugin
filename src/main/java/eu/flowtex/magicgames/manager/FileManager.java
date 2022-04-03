package eu.flowtex.magicgames.manager;

import eu.flowtex.magicgames.Main;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class FileManager {
    private static FileManager instance;
    public static final int TITAN_DATA = 200;
    public static final int TEAMS = 201;
    public static final int TIMER = 202;
    public static final int BANS = 203;
    public static final int LOG = 204;
    public static final int CONFIG = 205;
    private Function<Object, String> function;
    private File titandatapath;
    private ConfigurationNode titanconfig;
    private File teamspath;
    private ConfigurationNode teamsconfig;
    private File timerspath;
    private ConfigurationNode timersconfig;
    private File banspath;
    private ConfigurationNode bansconfig;
    private File logpath;
    private ConfigurationNode logconfig;
    private File config;
    private ConfigurationNode configconfig;

    static {
        FileManager.instance = null;
    }

    private FileManager() {
        this.function = new Function<Object, String>() {
            @Override
            public String apply(final Object obj) {
                return (String) obj;
            }
        };
    }

    public static FileManager getInstance() {
        if (FileManager.instance == null) {
            FileManager.instance = new FileManager();
        }
        return FileManager.instance;
    }

    public void testDefault(final String path, final Object value) {
        if (this.configconfig != null && this.get(205, path) == null) {
            this.write(205, path, value);
        }
    }

    public void loadAllFiles() {
        this.generateFolder(Main.getInstance().getDefaultPath());
        this.titandatapath = this.newFile("TitanData");
        this.teamspath = this.newFile("Teams");
        this.timerspath = this.newFile("Timer");
        this.banspath = this.newFile("Bans");
        this.logpath = this.newFile("Log");
        this.config = this.newFile("config");
        this.titanconfig = this.loadFile(this.titandatapath);
        this.teamsconfig = this.loadFile(this.teamspath);
        this.timersconfig = this.loadFile(this.timerspath);
        this.bansconfig = this.loadFile(this.banspath);
        this.logconfig = this.loadFile(this.logpath);
        this.configconfig = this.loadFile(this.config);
        this.testDefault("starting.countdown", 60);
        this.testDefault("playing.join_invincibility", 15);
        this.testDefault("combat.no_kick_range", 30);
        this.testDefault("combat.extra_time", 25);
        this.testDefault("border.world", "world");
        this.testDefault("border.x_coord", 0);
        this.testDefault("border.z_coord", 0);
        this.testDefault("border.borderradius", -1);
    }

    public void saveAllFiles() {
        this.saveFile(200);
        this.saveFile(201);
        this.saveFile(202);
        this.saveFile(203);
        this.saveFile(204);
        this.saveFile(205);
    }

    public ConfigurationNode getFileConfig(final int type) {
        switch (type) {
            case 200: {
                return this.titanconfig;
            }
            case 201: {
                return this.teamsconfig;
            }
            case 202: {
                return this.timersconfig;
            }
            case 203: {
                return this.bansconfig;
            }
            case 204: {
                return this.logconfig;
            }
            case 205: {
                return this.configconfig;
            }
            default: {
                return null;
            }
        }
    }

    public List<String> getStringList(final int type, final String path) {
        final ConfigurationNode configNode = this.getFileConfig(type);
        final List<String> list = (List<String>) configNode.getNode((Object[]) path.split("\\.")).getList((Function) this.function);
        return list;
    }

    public void saveFile(final int type) {
        switch (type) {
            case 200: {
                this.saveFile(this.titandatapath, this.titanconfig);
                break;
            }
            case 201: {
                this.saveFile(this.teamspath, this.teamsconfig);
                break;
            }
            case 202: {
                this.saveFile(this.timerspath, this.timersconfig);
                break;
            }
            case 203: {
                this.saveFile(this.banspath, this.bansconfig);
                break;
            }
            case 204: {
                this.saveFile(this.logpath, this.logconfig);
                break;
            }
            case 205: {
                this.saveFile(this.config, this.configconfig);
                break;
            }
        }
    }

    public int getActualInt(final int type, final String path) {
        int actual = 1;
        boolean hasIndex = true;
        while (hasIndex) {
            if (this.contains(type, String.valueOf(path) + "." + actual)) {
                ++actual;
            } else {
                --actual;
                hasIndex = false;
            }
        }
        return actual;
    }

    public int getNextInt(final int type, final String path) {
        int next = -1;
        for (boolean hasNext = true; hasNext; hasNext = false) {
            ++next;
            if (!this.contains(type, String.valueOf(path) + "." + (next + 1))) {
            }
        }
        return next + 1;
    }

    public void write(final int type, final String path, final Object value) {
        switch (type) {
            case 200: {
                this.write(this.titanconfig, this.titandatapath, path, value);
                break;
            }
            case 201: {
                this.write(this.teamsconfig, this.teamspath, path, value);
                break;
            }
            case 202: {
                this.write(this.timersconfig, this.timerspath, path, value);
                break;
            }
            case 203: {
                this.write(this.bansconfig, this.banspath, path, value);
                break;
            }
            case 204: {
                this.write(this.logconfig, this.logpath, path, value);
                break;
            }
            case 205: {
                this.write(this.configconfig, this.config, path, value);
                break;
            }
        }
    }

    public Object get(final int type, final String path) {
        Object obj = null;
        switch (type) {
            case 200: {
                obj = this.titanconfig.getNode((Object[]) path.split("\\.")).getValue();
                break;
            }
            case 201: {
                obj = this.teamsconfig.getNode((Object[]) path.split("\\.")).getValue();
                break;
            }
            case 202: {
                obj = this.timersconfig.getNode((Object[]) path.split("\\.")).getValue();
                break;
            }
            case 203: {
                obj = this.bansconfig.getNode((Object[]) path.split("\\.")).getValue();
                break;
            }
            case 204: {
                obj = this.logconfig.getNode((Object[]) path.split("\\.")).getValue();
                break;
            }
            case 205: {
                obj = this.configconfig.getNode((Object[]) path.split("\\.")).getValue();
                break;
            }
        }
        return obj;
    }

    public void remove(final int type, final String path) {
        switch (type) {
            case 200: {
                this.remove(this.titanconfig, this.titandatapath, path);
                break;
            }
            case 201: {
                this.remove(this.teamsconfig, this.teamspath, path);
                break;
            }
            case 202: {
                this.remove(this.timersconfig, this.timerspath, path);
                break;
            }
            case 203: {
                this.remove(this.bansconfig, this.banspath, path);
                break;
            }
            case 204: {
                this.remove(this.logconfig, this.logpath, path);
                break;
            }
            case 205: {
                this.remove(this.configconfig, this.config, path);
                break;
            }
        }
    }

    public void generateFolder(final String path) {
        final File folder = new File(path);
        try {
            if (!folder.exists()) {
                if (folder.mkdir()) {
                    System.out.println("Ordner erstellt in " + path);
                } else {
                    System.err.println("Ordner nicht erstellt in " + path);
                }
            }
        } catch (Exception e) {
            System.err.println("Error beim erstellen des Pfades " + path);
        }
    }

    public boolean contains(final int type, final String path) {
        boolean cont = false;
        switch (type) {
            case 200: {
                if (this.titanconfig.getNode((Object[]) path.split("\\.")).getValue() != null) {
                    cont = true;
                    break;
                }
                break;
            }
            case 201: {
                if (this.teamsconfig.getNode((Object[]) path.split("\\.")).getValue() != null) {
                    cont = true;
                    break;
                }
                break;
            }
            case 202: {
                if (this.timersconfig.getNode((Object[]) path.split("\\.")).getValue() != null) {
                    cont = true;
                    break;
                }
                break;
            }
            case 203: {
                if (this.bansconfig.getNode((Object[]) path.split("\\.")).getValue() != null) {
                    cont = true;
                    break;
                }
                break;
            }
            case 204: {
                if (this.logconfig.getNode((Object[]) path.split("\\.")).getValue() != null) {
                    cont = true;
                    break;
                }
                break;
            }
            case 205: {
                if (this.configconfig.getNode((Object[]) path.split("\\.")).getValue() != null) {
                    cont = true;
                    break;
                }
                break;
            }
        }
        return cont;
    }

    private void remove(final ConfigurationNode node, final File file, final String path) {
        final String[] nodes = path.split(".");
        final String[] nodesToGo = new String[nodes.length - 1];
        for (int i = 0; i <= nodes.length - 2; ++i) {
            nodesToGo[i] = nodes[i];
        }
        final String toRemove = nodes[nodes.length - 1];
        node.getNode((Object[]) nodesToGo).removeChild((Object) toRemove);
        this.saveFile(file, node);
    }

    private File newFile(final String name) {
        final File file = new File(String.valueOf(Main.getInstance().getDefaultPath()) + "/" + name + ".conf");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Fehler beim Erstellen der Datei: " + name + ".conf");
            }
        }
        return file;
    }

    private void saveFile(final File file, final ConfigurationNode root) {
        final ConfigurationLoader<CommentedConfigurationNode> manager = (ConfigurationLoader<CommentedConfigurationNode>) ((HoconConfigurationLoader.Builder) HoconConfigurationLoader.builder().setFile(file)).build();
        try {
            manager.save(root);
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern von " + file.getName());
        }
    }

    private void write(final ConfigurationNode node, final File file, final String path, final Object value) {
        node.getNode((Object[]) path.split("\\.")).setValue(value);
        this.saveFile(file, node);
    }

    private ConfigurationNode loadFile(final File file) {
        final ConfigurationLoader<CommentedConfigurationNode> manager = (ConfigurationLoader<CommentedConfigurationNode>) ((HoconConfigurationLoader.Builder) HoconConfigurationLoader.builder().setFile(file)).build();
        ConfigurationNode root = null;
        try {
            root = manager.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}
