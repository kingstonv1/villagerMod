package com.example.examplemod.Behaviors;

import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.villager.Villager;

import java.util.Map;

public class Spin extends Behavior<Villager> {
    public Spin(Map<MemoryModuleType<?>, MemoryStatus> entryCondition, int minDuration, int maxDuration) {
        super(entryCondition, minDuration, maxDuration);
    }
}
