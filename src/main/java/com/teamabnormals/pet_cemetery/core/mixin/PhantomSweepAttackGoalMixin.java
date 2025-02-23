package com.teamabnormals.pet_cemetery.core.mixin;

import com.google.common.collect.Lists;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Phantom.PhantomSweepAttackGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(PhantomSweepAttackGoal.class)
public class PhantomSweepAttackGoalMixin {

	@Redirect(method = "canContinueToUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Cat;hiss()V"))
	private void hiss(Cat cat) {
		if (cat.getMobType() != MobType.UNDEAD)
			cat.hiss();
	}

	@Redirect(method = "canContinueToUse", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
	private boolean isEmpty(List<Cat> cats) {
		List<Cat> toRemove = Lists.newArrayList();
		for (Cat cat : cats) {
			if (cat.getMobType() == MobType.UNDEAD) {
				toRemove.add(cat);
			}
		}
		cats.removeAll(toRemove);
		return !cats.isEmpty();
	}
}
