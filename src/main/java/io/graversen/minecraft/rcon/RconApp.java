package io.graversen.minecraft.rcon;

import io.graversen.minecraft.rcon.commands.builders.EffectCommandBuilder;
import io.graversen.minecraft.rcon.commands.builders.TellRawCommandBuilder;
import io.graversen.minecraft.rcon.commands.builders.TitleCommandBuilder;
import io.graversen.minecraft.rcon.commands.objects.DifficultyCommand;
import io.graversen.minecraft.rcon.commands.objects.EffectCommand;
import io.graversen.minecraft.rcon.commands.objects.TellRawCommand;
import io.graversen.minecraft.rcon.commands.objects.TitleCommand;
import io.graversen.minecraft.rcon.util.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RconApp
{
    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        final RconClient rconClient = RconClient.connect("localhost", "abc123");
        final Future<RconResponse> rconResponse = rconClient.sendRaw("/tellraw @a {\"keybind\":\"key.drop\"}\n");
        final RconResponse rconResponse1 = rconResponse.get();

        rconClient.rcon().tellRaw(new TellRawCommand(Selectors.ALL_PLAYERS.getSelectorString(), "Hallo dette er en test", Colors.DARK_PURPLE.getColorName(), false, true));
        System.out.println(rconClient.sendRaw("list").get().getResponseString());

        TellRawCommandBuilder tellRawCommandBuilder = new TellRawCommandBuilder();
        final TellRawCommand tellRawCommand1 = tellRawCommandBuilder.targeting("MrSkurk").withColor(Colors.GREEN).withText("Hej med dig fra TellRawCommandBuilder").build();
        final TellRawCommand tellRawCommand2 = tellRawCommandBuilder.targeting("MrSkurk").withColor(Colors.YELLOW).withText(" (med lidt ekstra her)").build();
        rconClient.rcon().tellRaw(tellRawCommand1, tellRawCommand2);

        final List<String> playerList = rconClient.rcon().playerList();

        EffectCommandBuilder effectCommandBuilder = new EffectCommandBuilder();
        final EffectCommand effectCommand = effectCommandBuilder.targeting(Selectors.ALL_PLAYERS).withEffect(Effects.JUMP_BOOST, 5).withDuration(120).build();

        rconClient.rcon().effect(effectCommand);

        rconClient.rcon().difficulty(Difficulties.PEACEFUL);

        TitleCommandBuilder titleCommandBuilder = new TitleCommandBuilder();
        final TitleCommand title1 = titleCommandBuilder.atPosition(TitlePositions.TITLE).targeting(Selectors.ALL_PLAYERS).withColor(Colors.WHITE).withText("Welcome to The Game").build();
        final TitleCommand title2 = titleCommandBuilder.atPosition(TitlePositions.SUBTITLE).targeting(Selectors.ALL_PLAYERS).withColor(Colors.GRAY).withText("You just lost it...").italic().build();
        final TitleCommand title3 = titleCommandBuilder.atPosition(TitlePositions.ACTIONBAR).targeting(Selectors.ALL_PLAYERS).withColor(Colors.AQUA).obfuscated().withText("Hi there").build();

        rconClient.rcon().title(title1);
        rconClient.rcon().title(title2);
        rconClient.rcon().title(title3);

        System.out.println(rconClient.rcon().seed());
    }
}