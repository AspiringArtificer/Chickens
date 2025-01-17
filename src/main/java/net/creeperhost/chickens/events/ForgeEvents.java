package net.creeperhost.chickens.events;

import net.creeperhost.chickens.ChickensMod;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.init.ModEntities;
import net.creeperhost.chickens.registry.ChickensRegistry;
import net.creeperhost.chickens.registry.ChickensRegistryItem;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChickensMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents
{
    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event)
    {
        ModEntities.SPAWNABLE_CHICKENS.forEach(entityType ->
        {
            try
            {
                event.getSpawns().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(entityType, ChickensRegistry.getByResourceLocation(entityType.getRegistryName()).getSpawnWeight(), 2, 4));
            }
            catch (Exception ignored){}
        });
    }

    @SubscribeEvent
    public static void playerInteractEvent(PlayerInteractEvent.EntityInteract event)
    {
        Player player = event.getPlayer();
        Level level = event.getWorld();
        if(!player.getItemInHand(event.getHand()).isEmpty() && player.getItemInHand(event.getHand()).getItem() == Items.BOOK)
        {
            Entity entity = event.getTarget();
            if(entity.getType() == EntityType.CHICKEN)
            {
                EntityType<?> entityType = Registry.ENTITY_TYPE.get(ChickensRegistry.SMART_CHICKEN_ID);
                EntityChickensChicken chicken = (EntityChickensChicken) entityType.create(level);
                if(chicken != null)
                {
                    chicken.setPos(entity.position());
                    level.addFreshEntity(chicken);
                    entity.remove(Entity.RemovalReason.DISCARDED);
                    if (!player.isCreative())
                    {
                        player.getItemInHand(event.getHand()).shrink(1);
                    }
                }
            }
        }
        else if(!player.getItemInHand(event.getHand()).isEmpty() && player.getItemInHand(event.getHand()).getItem() == Items.NETHER_STAR
            && ChickensRegistry.getByResourceLocation(ChickensRegistry.DANGEROUS_WITHER_CHICKEN_ID).isEnabled())
        {
            Entity entity = event.getTarget();
            if(entity.getType() == Registry.ENTITY_TYPE.get(ChickensRegistry.WITHER_CHICKEN_ID))
            {
                EntityType<?> entityType = Registry.ENTITY_TYPE.get(ChickensRegistry.DANGEROUS_WITHER_CHICKEN_ID);
                EntityChickensChicken chicken = (EntityChickensChicken) entityType.create(level);
                if(chicken != null)
                {
                    chicken.setPos(entity.position());
                    level.addFreshEntity(chicken);
                    entity.remove(Entity.RemovalReason.DISCARDED);
                    if (!player.isCreative())
                    {
                        player.getItemInHand(event.getHand()).shrink(1);
                    }
                }
            }
        }
        else if(!player.getItemInHand(event.getHand()).isEmpty() && player.getItemInHand(event.getHand()).getItem() == Items.DRAGON_EGG
                && ChickensRegistry.getByResourceLocation(ChickensRegistry.NOTCH_CHICKEN_ID).isEnabled())
        {
            Entity entity = event.getTarget();
            if(entity.getType() == Registry.ENTITY_TYPE.get(ChickensRegistry.SMART_CHICKEN_ID))
            {
                EntityType<?> entityType = Registry.ENTITY_TYPE.get(ChickensRegistry.NOTCH_CHICKEN_ID);
                EntityChickensChicken chicken = (EntityChickensChicken) entityType.create(level);
                if(chicken != null)
                {
                    chicken.setPos(entity.position());
                    level.addFreshEntity(chicken);
                    entity.remove(Entity.RemovalReason.DISCARDED);
                    if (!player.isCreative())
                    {
                        player.getItemInHand(event.getHand()).shrink(1);
                    }
                }
            }
        }
    }
}
