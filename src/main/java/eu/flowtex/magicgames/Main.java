package eu.flowtex.magicgames;

import eu.flowtex.magicgames.commands.*;
import eu.flowtex.magicgames.listeners.*;
import eu.flowtex.magicgames.manager.FileManager;
import eu.flowtex.magicgames.manager.TeamManager;
import eu.flowtex.magicgames.manager.TimerManager;
import eu.flowtex.magicgames.utils.Data;
import eu.flowtex.magicgames.utils.GameState;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

//import eu.flowtex.magicgames.utils.SaveInvManager;

@Plugin(
        id = "magicgames",
        name = "MagicGamesVI",
        version = "1.0",
        authors = "Ramil, yNikk",
        description = "Plugin for MagicGames VI"
)

public class Main {
    public Game game;
    public String configDirection;
    private static Main instance;
    private GameState state;

    public Main() {
        this.game = Sponge.getGame();
        this.configDirection = "config/MagicGames";
    }

    @Listener // ON_ENABLE
    public void onStart(final GameStartedServerEvent e) {
        Main.instance = this;
        FileManager.getInstance().loadAllFiles();
        TeamManager.getInstance().loadTeams();
        TimerManager.getInstance().loadTracked(this);
        TimerManager.getInstance().initiateDailyTimerTask(this);
        this.registerCommands();
        this.registerListeners();
        Data.activateBorder();
        final FileManager fm = FileManager.getInstance();
        if (!fm.contains(200, "GameState")) {
            this.setState(GameState.PREPARATION);
        } else {
            final String stateString = (String) fm.get(200, "GameState");
            this.setState(GameState.valueOf(stateString));
        }
    }

    @Listener // ON_DISABLE
    public void onStop(final GameStoppedServerEvent e) {
        TeamManager.getInstance().saveTeams();
        TimerManager.getInstance().saveTracked();
        FileManager.getInstance().saveAllFiles();
        for (final Task t : Sponge.getScheduler().getScheduledTasks()) {
            t.cancel();
        }
    }

    private void registerListeners() {
        final EventManager eventManager = this.game.getEventManager();
        //final SaveInvManager saveinv = new SaveInvManager();
        //MinecraftForge.EVENT_BUS.register((Object) saveinv);
        //FMLCommonHandler.instance().bus().register((Object) saveinv);
        eventManager.registerListeners((Object) this, (Object) new AITaskListener());
        eventManager.registerListeners((Object) this, (Object) new EntityDamageListener());
        eventManager.registerListeners((Object) this, (Object) new PlayerChatListener());
        eventManager.registerListeners((Object) this, (Object) new PlayerDeathListener());
        eventManager.registerListeners((Object) this, (Object) new PlayerJoinListener());
        eventManager.registerListeners((Object) this, (Object) new PlayerPreJoinListener());
        eventManager.registerListeners((Object) this, (Object) new PlayerQuitListener());
        eventManager.registerListeners((Object) this, (Object) new PlayerRespawnListener());
    }

    public void registerCommands() {
        final CommandSpec createTeam = CommandSpec.builder().description((Text) Text.of("Erstellt ein Team")).permission("titan.admin").arguments(new CommandElement[]{GenericArguments.string((Text) Text.of("teamname")), GenericArguments.optional(GenericArguments.string((Text) Text.of("playername1"))), GenericArguments.optional(GenericArguments.string((Text) Text.of("playername2")))}).executor((CommandExecutor) new TitanTeamCommand()).build();
        final CommandSpec removeTeam = CommandSpec.builder().description((Text) Text.of("L\u00f6scht ein Team")).permission("titan.admin").arguments(GenericArguments.string((Text) Text.of("teamname"))).executor((CommandExecutor) new TitanTeamRemoveCommand()).build();
        final CommandSpec addPlayer = CommandSpec.builder().description((Text) Text.of("F\u00fcgt einen Spieler einem Team hinzu")).permission("titan.admin").arguments(new CommandElement[]{GenericArguments.string((Text) Text.of("teamname")), GenericArguments.string((Text) Text.of("playername1"))}).executor((CommandExecutor) new TitanPlayerCommand()).build();
        final CommandSpec removePlayer = CommandSpec.builder().description((Text) Text.of("Entfernt einen Spieler aus einem Team")).permission("titan.admin").arguments(new CommandElement[]{GenericArguments.string((Text) Text.of("teamname")), GenericArguments.string((Text) Text.of("playername1"))}).executor((CommandExecutor) new TitanPlayerRemoveCommand()).build();
        final CommandSpec setTeamColor = CommandSpec.builder().description((Text) Text.of("Setzt eine Teamfarbe")).permission("titan.admin").arguments(new CommandElement[]{GenericArguments.string((Text) Text.of("teamname")), GenericArguments.string((Text) Text.of("color"))}).executor((CommandExecutor) new TitanSetColorCommand()).build();
        final CommandSpec addSpectator = CommandSpec.builder().description((Text) Text.of("Adds a Spectator")).permission("titan.admin").arguments(GenericArguments.string((Text) Text.of("playername"))).executor((CommandExecutor) new SpectatorCommand()).build();
        final CommandSpec removeSpectator = CommandSpec.builder().description((Text) Text.of("Removes a Spectator")).permission("titan.admin").arguments(GenericArguments.string((Text) Text.of("playername"))).executor((CommandExecutor) new SpectatorRemoveCommand()).build();
        final CommandSpec titancommand = CommandSpec.builder().description((Text) Text.of("Titaneinstellungen")).permission("titan.admin").child((CommandCallable) createTeam, new String[]{"createTeam"}).child((CommandCallable) removeTeam, new String[]{"removeTeam"}).child((CommandCallable) addPlayer, new String[]{"addPlayer"}).child((CommandCallable) removePlayer, new String[]{"removePlayer"}).child((CommandCallable) setTeamColor, new String[]{"setTeamColor"}).child((CommandCallable) addSpectator, new String[]{"addSpec", "addSpectator"}).child((CommandCallable) removeSpectator, new String[]{"removeSpectator", "removeSpec"}).build();
        this.game.getCommandManager().register((Object) this, (CommandCallable) titancommand, new String[]{"titan"});
        final CommandSpec teamscommand = CommandSpec.builder().description((Text) Text.of("Gibt alle Teams mit ihren Leben an.")).executor((CommandExecutor) new TeamsCommand()).build();
        this.game.getCommandManager().register((Object) this, (CommandCallable) teamscommand, new String[]{"teams", "Teams"});
        final CommandSpec startcommand = CommandSpec.builder().description((Text) Text.of("Startet Lucky Titan.")).permission("titan.admin").executor((CommandExecutor) new StartCommand(this)).build();
        this.game.getCommandManager().register((Object) this, (CommandCallable) startcommand, new String[]{"start"});
        final CommandSpec timeCommand = CommandSpec.builder().description((Text) Text.of("Zeigt an wieviel Zeit man noch hat.")).executor((CommandExecutor) new TimeCommand()).build();
        this.game.getCommandManager().register((Object) this, (CommandCallable) timeCommand, new String[]{"time"});
    }

    public static Main getInstance() {
        return Main.instance;
    }

    public String getDefaultPath() {
        return this.configDirection;
    }

    public boolean isState(final GameState state) {
        return this.state.equals(state);
    }

    public void setState(final GameState state) {
        this.state = state;
        FileManager.getInstance().write(200, "GameState", this.state.toString());
    }
}
