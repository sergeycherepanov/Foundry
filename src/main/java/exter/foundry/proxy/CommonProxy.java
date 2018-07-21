package exter.foundry.proxy;

import exter.foundry.container.ContainerAlloyMixer;
import exter.foundry.container.ContainerAlloyingCrucible;
import exter.foundry.container.ContainerBurnerHeater;
import exter.foundry.container.ContainerMaterialRouter;
import exter.foundry.container.ContainerMeltingCrucible;
import exter.foundry.container.ContainerMetalCaster;
import exter.foundry.container.ContainerMetalInfuser;
import exter.foundry.container.ContainerMoldStation;
import exter.foundry.gui.GuiAlloyMixer;
import exter.foundry.gui.GuiAlloyingCrucible;
import exter.foundry.gui.GuiBurnerHeater;
import exter.foundry.gui.GuiMaterialRouter;
import exter.foundry.gui.GuiMeltingCrucible;
import exter.foundry.gui.GuiMetalCaster;
import exter.foundry.gui.GuiMetalInfuser;
import exter.foundry.gui.GuiMoldStation;
import exter.foundry.tileentity.TileEntityAlloyMixer;
import exter.foundry.tileentity.TileEntityAlloyingCrucible;
import exter.foundry.tileentity.TileEntityBurnerHeater;
import exter.foundry.tileentity.TileEntityMaterialRouter;
import exter.foundry.tileentity.TileEntityMeltingCrucibleBasic;
import exter.foundry.tileentity.TileEntityMetalCaster;
import exter.foundry.tileentity.TileEntityMetalInfuser;
import exter.foundry.tileentity.TileEntityMoldStation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
    static public final int GUI_CRUCIBLE = 0;
    static public final int GUI_CASTER = 1;
    static public final int GUI_ALLOYMIXER = 2;
    static public final int GUI_INFUSER = 3;
    static public final int GUI_MATERIALROUTER = 4;
    static public final int GUI_MOLDSTATION = 5;
    static public final int GUI_BURNERHEATER = 6;
    static public final int GUI_ALLOYINGCRUCIBLE = 7;

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        switch (ID)
        {
        case GUI_CRUCIBLE:
        {
            TileEntityMeltingCrucibleBasic te = (TileEntityMeltingCrucibleBasic) world.getTileEntity(pos);
            return new GuiMeltingCrucible(te, player);
        }
        case GUI_CASTER:
        {
            TileEntityMetalCaster te = (TileEntityMetalCaster) world.getTileEntity(pos);
            return new GuiMetalCaster(te, player);
        }
        case GUI_ALLOYMIXER:
        {
            TileEntityAlloyMixer te = (TileEntityAlloyMixer) world.getTileEntity(pos);
            return new GuiAlloyMixer(te, player);
        }
        case GUI_INFUSER:
        {
            TileEntityMetalInfuser te = (TileEntityMetalInfuser) world.getTileEntity(pos);
            return new GuiMetalInfuser(te, player);
        }
        case GUI_MATERIALROUTER:
        {
            TileEntityMaterialRouter te = (TileEntityMaterialRouter) world.getTileEntity(pos);
            return new GuiMaterialRouter(te, player);
        }
        case GUI_MOLDSTATION:
        {
            TileEntityMoldStation te = (TileEntityMoldStation) world.getTileEntity(pos);
            return new GuiMoldStation(te, player);
        }
        case GUI_BURNERHEATER:
        {
            TileEntityBurnerHeater te = (TileEntityBurnerHeater) world.getTileEntity(pos);
            return new GuiBurnerHeater(te, player);
        }
        case GUI_ALLOYINGCRUCIBLE:
        {
            TileEntityAlloyingCrucible te = (TileEntityAlloyingCrucible) world.getTileEntity(pos);
            return new GuiAlloyingCrucible(te, player);
        }
        }
        return null;
    }

    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        switch (ID)
        {
        case GUI_CRUCIBLE:
            return new ContainerMeltingCrucible((TileEntityMeltingCrucibleBasic) world.getTileEntity(pos), player);
        case GUI_CASTER:
            return new ContainerMetalCaster((TileEntityMetalCaster) world.getTileEntity(pos), player);
        case GUI_ALLOYMIXER:
            return new ContainerAlloyMixer((TileEntityAlloyMixer) world.getTileEntity(pos), player);
        case GUI_INFUSER:
            return new ContainerMetalInfuser((TileEntityMetalInfuser) world.getTileEntity(pos), player);
        case GUI_MATERIALROUTER:
            return new ContainerMaterialRouter((TileEntityMaterialRouter) world.getTileEntity(pos), player);
        case GUI_MOLDSTATION:
            return new ContainerMoldStation((TileEntityMoldStation) world.getTileEntity(pos), player);
        case GUI_BURNERHEATER:
            return new ContainerBurnerHeater((TileEntityBurnerHeater) world.getTileEntity(pos), player);
        case GUI_ALLOYINGCRUCIBLE:
            return new ContainerAlloyingCrucible((TileEntityAlloyingCrucible) world.getTileEntity(pos), player);
        }
        return null;
    }

    public void init()
    {
    }

    public void postInit()
    {
    }

    public void preInit()
    {
    }

    public String translate(String string, Object... args)
    {
        return I18n.format(string, args);
    }
}
