package exter.foundry.api;

import exter.foundry.api.heatable.IHeatProvider;
import exter.foundry.api.material.IMaterialRegistry;
import exter.foundry.api.recipe.manager.IAlloyFurnaceRecipeManager;
import exter.foundry.api.recipe.manager.IAlloyMixerRecipeManager;
import exter.foundry.api.recipe.manager.IAlloyingCrucibleRecipeManager;
import exter.foundry.api.recipe.manager.IAtomizerRecipeManager;
import exter.foundry.api.recipe.manager.IBurnerHeaterFuelManager;
import exter.foundry.api.recipe.manager.ICastingRecipeManager;
import exter.foundry.api.recipe.manager.ICastingTableRecipeManager;
import exter.foundry.api.recipe.manager.IFluidHeaterFuelManager;
import exter.foundry.api.recipe.manager.IInfuserRecipeManager;
import exter.foundry.api.recipe.manager.IMeltingRecipeManager;
import exter.foundry.api.recipe.manager.IMoldRecipeManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * API for recipes of Foundry machines.
 */
public class FoundryAPI
{

    /**
     * The base value foundry uses for all liquid operations.  Set by foundry configs, defaulting to 108.
     */
    public static int FLUID_AMOUNT_INGOT = 144;

    /**
     * The fluid value for an ore, or 0, if the config is not overriding it.
     */
    public static int FLUID_AMOUNT_ORE = FLUID_AMOUNT_INGOT * 2;

    public static int getAmountBlock()
    {
        return FLUID_AMOUNT_INGOT * 9;
    }

    public static int getAmountPlate()
    {
        return FLUID_AMOUNT_INGOT;
    }

    public static int getAmountRod()
    {
        return FLUID_AMOUNT_INGOT / 2;
    }

    public static int getAmountNugget()
    {
        return FLUID_AMOUNT_INGOT / 9;
    }

    public static int getAmountOre()
    {
        return FLUID_AMOUNT_ORE == 0 ? FLUID_AMOUNT_INGOT * 2 : FLUID_AMOUNT_ORE;
    }

    public static int getAmountGear()
    {
        return FLUID_AMOUNT_INGOT * 4;
    }

    public static int getAmountTrapdoor()
    {
        return FLUID_AMOUNT_INGOT * 4;
    }

    public static int getAmountHelm()
    {
        return FLUID_AMOUNT_INGOT * 5;
    }

    public static int getAmountChest()
    {
        return FLUID_AMOUNT_INGOT * 8;
    }

    public static int getAmountLegs()
    {
        return FLUID_AMOUNT_INGOT * 7;
    }

    public static int getAmountBoots()
    {
        return FLUID_AMOUNT_INGOT * 4;
    }

    public static int getAmountPickaxe()
    {
        return FLUID_AMOUNT_INGOT * 3;
    }

    public static int getAmountShovel()
    {
        return FLUID_AMOUNT_INGOT * 1;
    }

    public static int getAmountAxe()
    {
        return FLUID_AMOUNT_INGOT * 3;
    }

    public static int getAmountHoe()
    {
        return FLUID_AMOUNT_INGOT * 2;
    }

    public static int getAmountSword()
    {
        return FLUID_AMOUNT_INGOT * 2;
    }

    public static int getAmountHammer()
    {
        return FLUID_AMOUNT_INGOT * 5;
    }

    public static int getAmountSickle()
    {
        return FLUID_AMOUNT_INGOT * 3;
    }

    public static int getAmountExcavator()
    {
        return FLUID_AMOUNT_INGOT * 3;
    }

    public static int getAmountShield()
    {
        return FLUID_AMOUNT_INGOT * 6;
    }

    public static int getAmountShears()
    {
        return FLUID_AMOUNT_INGOT * 2;
    }

    public static int getAmountButton()
    {
        return getAmountNugget() * 2;
    }

    public static int getAmountWall()
    {
        return getAmountBlock();
    }

    public static int getAmountLever()
    {
        return getAmountRod() + FLUID_AMOUNT_INGOT;
    }

    public static int getAmountCasing()
    {
        return FLUID_AMOUNT_INGOT / 2;
    }

    public static int getAmountClump()
    {
        return FLUID_AMOUNT_INGOT;
    }

    public static int getAmountCrystal()
    {
        return FLUID_AMOUNT_INGOT;
    }

    public static int getAmountShard()
    {
        return FLUID_AMOUNT_INGOT;
    }

    public static int getAmountDensePlate()
    {
        return getAmountPlate() * 9;
    }

    public static int getAmountBars()
    {
        return 54;
    }

    public static int getAmountSlab()
    {
        return getAmountBlock() / 2;
    }

    public static int getAmountStairs()
    {
        return getAmountBlock() * 3 / 2;
    }

    public static int getAmountPressurePlate()
    {
        return FLUID_AMOUNT_INGOT * 2;
    }

    /**
     * Tank capacity for machines.
     */
    public static final int CRUCIBLE_TANK_CAPACITY = 6000;
    public static final int CASTER_TANK_CAPACITY = 6000;
    public static final int INFUSER_TANK_CAPACITY = 5000;
    public static final int ALLOYMIXER_TANK_CAPACITY = 2000;
    public static final int ALLOYING_CRUCIBLE_TANK_CAPACITY = 3000;
    public static final int ATOMIZER_TANK_CAPACITY = 6000;
    public static final int ATOMIZER_WATER_TANK_CAPACITY = 6000;

    //Heat loss rates for crucibles.
    public static final int CRUCIBLE_BASIC_TEMP_LOSS_RATE = 75;
    public static final int CRUCIBLE_STANDARD_TEMP_LOSS_RATE = 80;
    public static final int CRUCIBLE_ADVANCED_TEMP_LOSS_RATE = 90;

    //Max temperatures for crucibles (in 1/100 deg Ks).
    public static int CRUCIBLE_BASIC_MAX_TEMP = 200000;
    public static int CRUCIBLE_STANDARD_MAX_TEMP = 250000;
    public static int CRUCIBLE_ADVANCED_MAX_TEMP = 400000;

    //These fields are set by Foundry during it's preInit phase.
    public static IMeltingRecipeManager MELTING_MANAGER;
    public static ICastingRecipeManager CASTING_MANAGER;
    public static ICastingTableRecipeManager CASTING_TABLE_MANAGER;
    public static IAlloyMixerRecipeManager ALLOY_MIXER_MANAGER;
    public static IAlloyingCrucibleRecipeManager ALLOYING_CRUCIBLE_MANAGER;
    public static IInfuserRecipeManager INFUSER_MANAGER;
    public static IAlloyFurnaceRecipeManager ALLOY_FURNACE_MANAGER;
    public static IAtomizerRecipeManager ATOMIZER_MANAGER;
    public static IMoldRecipeManager MOLD_MANAGER;
    public static IBurnerHeaterFuelManager BURNER_HEATER_FUEL;
    public static IFluidHeaterFuelManager FLUID_HEATER_FUEL;

    public static IMaterialRegistry MATERIALS;

    @CapabilityInject(IHeatProvider.class)
    public static Capability<IHeatProvider> HEAT_PROVIDER_CAP;
}
