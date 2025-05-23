package com.sp.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.sp.block.SprintBlockSoundGroup;
import net.minecraft.entity.Entity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class PlaySprintSoundMixin {

    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Redirect(method = "playStepSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
    private void playSprintSound(Entity instance, SoundEvent sound, float volume, float pitch, @Local BlockSoundGroup blockSoundGroup){
        if(blockSoundGroup instanceof SprintBlockSoundGroup && instance.isSprinting()) {
            this.playSound(((SprintBlockSoundGroup) blockSoundGroup).getSprintingSound(), blockSoundGroup.getVolume(), blockSoundGroup.getPitch());
        } else {
            this.playSound(blockSoundGroup.getStepSound(), blockSoundGroup.getVolume() * 0.15f, blockSoundGroup.getPitch());
        }
    }

}
