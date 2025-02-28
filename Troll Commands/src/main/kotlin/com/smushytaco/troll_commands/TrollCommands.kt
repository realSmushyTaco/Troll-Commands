package com.smushytaco.troll_commands
import com.smushytaco.troll_commands.commands.base.TrollCommand
import com.smushytaco.troll_commands.commands.base.TrollKickCommand
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
object TrollCommands : ModInitializer {
    const val MOD_ID = "troll_commands"
    private val config = ModConfig.createAndLoad()
    private val JUMPSCARE_IDENTIFIER = Identifier.of(MOD_ID, "jumpscare")
    private val JUMPSCARE = SoundEvent.of(JUMPSCARE_IDENTIFIER)
    private val RICK_ROLL_IDENTIFIER = Identifier.of(MOD_ID, "rick_roll")
    private val RICK_ROLL = SoundEvent.of(RICK_ROLL_IDENTIFIER)
    private val PUMPKIN_IDENTIFIER = Identifier.of(MOD_ID, "pumpkin")
    private val PUMPKIN = SoundEvent.of(PUMPKIN_IDENTIFIER)
    private val REPLAY_IDENTIFIER = Identifier.of(MOD_ID, "replay")
    private val REPLAY = SoundEvent.of(REPLAY_IDENTIFIER)
    lateinit var trollCommands: HashSet<TrollCommand>
        private set
    override fun onInitialize() {
        Registry.register(Registries.SOUND_EVENT, JUMPSCARE_IDENTIFIER, JUMPSCARE)
        Registry.register(Registries.SOUND_EVENT, RICK_ROLL_IDENTIFIER, RICK_ROLL)
        Registry.register(Registries.SOUND_EVENT, PUMPKIN_IDENTIFIER, PUMPKIN)
        Registry.register(Registries.SOUND_EVENT, REPLAY_IDENTIFIER, REPLAY)
        trollCommands = hashSetOf(
            TrollCommand("amongus", { config.canBeAmongUsed }, arrayOf("textures/amongus_command/among_us.png")),
            TrollKickCommand("crash", { config.canBeCrashed }, arrayOf("textures/crash_command/anus.png", "textures/crash_command/burned.png", "textures/crash_command/dog.png", "textures/crash_command/dog2.png", "textures/crash_command/dog3.png", "textures/crash_command/furries.png", "textures/crash_command/goat.png", "textures/crash_command/hotdog.png", "textures/crash_command/sock.png")),
            TrollCommand("hotdog", { config.canBeHotDogged }, arrayOf("textures/hotdog_command/hot_dog.png")),
            TrollCommand("jumpscare", { config.canBeJumpscared }, arrayOf("textures/jumpscare_command/jumpscare.png"), JUMPSCARE),
            TrollCommand("pumpkin", { config.canBePumpkined }, null, PUMPKIN),
            TrollCommand("replay", { config.canBeReplayed }, null, REPLAY),
            TrollCommand("rickroll", { config.canBeRickRolled }, null, RICK_ROLL)
        )
        trollCommands.forEach { it.emptyPayload.register() }
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _, _ ->
            trollCommands.forEach { it.register(dispatcher) } })
    }
}