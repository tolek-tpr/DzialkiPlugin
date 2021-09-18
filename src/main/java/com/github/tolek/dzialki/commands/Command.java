package com.github.tolek.dzialki.commands;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

abstract public class Command implements CommandExecutor {

    private String command;
    private String description;
    private boolean onlyPlayer;
    private boolean onlyOp;
    private List<Pair<String, String>> mandatoryArgs = new ArrayList<>();
    private List<Pair<String, String>> optionalArgs = new ArrayList<>();

    public Command(String command, String description, boolean onlyPlayer, boolean onlyOp, List<Pair<String, String>> mandatoryArgs, List<Pair<String, String>> optionalArgs) {
        this.command = command;
        this.description = description;
        this.onlyPlayer = onlyPlayer;
        this.onlyOp = onlyOp;
        this.mandatoryArgs = mandatoryArgs;
        this.optionalArgs = optionalArgs;
    }

    static boolean info(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Dzialki: "
                + ChatColor.RESET + ChatColor.DARK_GREEN + message);
        return true;
    }

    static boolean warning(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "WARNING: "
                + ChatColor.RESET + ChatColor.GRAY + message);
        return false;
    }

    static boolean error(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "ERROR: "
                + ChatColor.RESET + ChatColor.DARK_RED + message);
        return false;
    }

    public static String formatArgs(List<Pair<String, String>> args) {
        return args.stream().map(arg -> " * <" + arg.getLeft() + "> - " + arg.getRight()).collect(Collectors.joining("\n"));
    }

    private void printHelp() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "HELP: "
                + ChatColor.RESET + ChatColor.DARK_BLUE + description + ChatColor.RESET);
        if (mandatoryArgs.size() > 0)
            Bukkit.getServer().getConsoleSender().sendMessage(formatArgs(mandatoryArgs));
        if (optionalArgs.size() > 0)
            Bukkit.getServer().getConsoleSender().sendMessage("\nOptional parameters:\n" + formatArgs(optionalArgs));
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (onlyPlayer && !(sender instanceof Player)) {
            System.out.println("This command is only available to players");
            return false;
        }

        if (onlyOp && !sender.isOp()) {
            return error("Only op is allowed to use this command");
        }

        if (args.length < mandatoryArgs.size()) {
            printHelp();
            return warning("Missing mandatory parameters");
        }

        return handleCommand(sender, cmd, label, args);
    }

    protected abstract boolean handleCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args);

}