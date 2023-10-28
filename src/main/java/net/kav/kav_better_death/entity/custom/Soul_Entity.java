package net.kav.kav_better_death.entity.custom;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kav.kav_better_death.networking.ModMessages;
import net.kav.kav_better_death.utils.HealthData;
import net.kav.kav_better_death.utils.IEntityDataSaver;
import net.kav.kav_better_death.utils.Soul_counterData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Optional;
import java.util.UUID;

public class Soul_Entity extends PathAwareEntity implements IAnimatable {
    private int particleSpawnCounter = 0;
    private static final int GLOW_PARTICLE_FREQUENCY = 30; // Adjust the frequency of particles
    private static final double SPREAD_FACTOR = 0.3;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);


    private UUID player;
    private int EXPERIENCE;

    public Soul_Entity(EntityType<? extends Soul_Entity> entityType, World world) {
        super(entityType, world);

    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0f);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.soul.spin", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
        this.emitGameEvent(GameEvent.ENTITY_DIE);
    }
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
    @Override
    public void tick() {
        super.tick();
        this.playSound(SoundEvents.BLOCK_BEACON_AMBIENT, 0.5f, 1.2f); // Adjust the pitch as needed
        if (world.isClient) {

            // Client-side handling
            particleSpawnCounter++;

            if (particleSpawnCounter >= GLOW_PARTICLE_FREQUENCY) {
                // Spawn the glow particles with spread around the entity's position
                for (int i = 0; i < 20; i++) { // Higher number of particles
                    double offsetX = (world.random.nextDouble() - 0.5) * SPREAD_FACTOR*2.2;
                    double offsetY = world.random.nextDouble() * SPREAD_FACTOR*4;
                    double offsetZ = (world.random.nextDouble() - 0.5) * SPREAD_FACTOR*2.2;

                    // Use one of the glow particle types (e.g., ParticleTypes.END_ROD or ParticleTypes.SOUL)
                    ParticleEffect particleType = ParticleTypes.END_ROD;

                    world.addParticle(particleType, getX() + offsetX, getY() + offsetY, getZ() + offsetZ, 0.0, 0.0, 0.0);
                }


                for (int i = 0; i < 5; i++) { // You can adjust the number of blood particles
                    double offsetX = (world.random.nextDouble() - 0.5) * SPREAD_FACTOR * 1.2;
                    double offsetY = world.random.nextDouble() * SPREAD_FACTOR * 1.1;
                    double offsetZ = (world.random.nextDouble() - 0.5) * SPREAD_FACTOR * 1.2;

                    ParticleEffect bloodParticleType = ParticleTypes.DRIPPING_LAVA; // You can choose a different blood particle type

                    world.addParticle(bloodParticleType, getX() + offsetX, getY() + offsetY, getZ() + offsetZ, 0.0, 0.0, 0.0);
                }

                particleSpawnCounter = 0;
            }
        }
       // System.out.println(this.getPlayerEXP());
    }

    @Override
    public ActionResult interactMob(PlayerEntity player2, Hand hand) {
        // This method will be called when the player right-clicks on the entity

        if (!this.world.isClient()) {
            // Server-side handling

            if(player2.getUuid().equals(getPlayerUUID()))
            {
                boolean soul= false;
                System.out.println(this.getPlayerEXP());
                Soul_counterData.setsoul(((IEntityDataSaver) player2),soul);

                double maxHealth = player2.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue();
                double targetMaxHealth = HealthData.gethealth((IEntityDataSaver) player2);
                player2.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(maxHealth+targetMaxHealth);

                player2.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.PLAYERS, 1.0f, 1.2f); // Adjust the pitch as needed
                player2.addExperienceLevels(this.getPlayerEXP());
                this.setPlayerEXP(0);

                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBoolean(soul);

                Soul_counterData.setsoul(((IEntityDataSaver) player2),soul);
                ServerPlayNetworking.send((ServerPlayerEntity) player2, ModMessages.SOUL,buf);

                HealthData.sethealth((IEntityDataSaver) player2,0);
                this.kill();
            }


        }



        return ActionResult.success(this.world.isClient);
    }
    //
    @Override
    public boolean damage(DamageSource source, float amount) {
        // Override the damage method so the entity doesn't take damage
        // Returning false means the entity won't be damaged by anything
        return false;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_BEACON_AMBIENT;
    }
    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        // Make the entity invulnerable to all damage sources
        return true;
    }

    @Override
    public void tickMovement() {
        // Override the tickMovement method so the entity won't move at all
        // If you want to keep this method for other purposes, you can remove its contents.
    }

//TRACKDATA
    @Nullable
    public UUID getPlayerUUID()
    {
        return player;
    }
    public void setPlayerUUID(@Nullable UUID uuid) {
        player=uuid;
    }


    public int getPlayerEXP()
    {
        return this.EXPERIENCE;
    }
    public void setPlayerEXP(int exp) {
     this.EXPERIENCE=exp;
    }
//nbt

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        // Save the PERMANENTUUID to NBT
        if(getPlayerUUID()!=null)
        {
            System.out.println(getPlayerUUID()+" save");
            nbt.putUuid("PermanentUUID", getPlayerUUID());
        }

        nbt.putInt("exp",getPlayerEXP());

    }

    // Override this method to read custom data from NBT
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        // Load the PERMANENTUUID from NBT
        if (nbt.contains("PermanentUUID")) {

            setPlayerUUID(nbt.getUuid("PermanentUUID"));
            System.out.println(getPlayerUUID()+ " load");
        }
        setPlayerEXP(nbt.getInt("exp"));
    }

    // Custom getter for the PERMANENTUUID

}
