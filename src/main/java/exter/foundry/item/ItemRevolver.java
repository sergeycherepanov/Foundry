package exter.foundry.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exter.foundry.ModFoundry;
import exter.foundry.api.firearms.IFirearmAmmo;
import exter.foundry.creativetab.FoundryTabFluids;
import exter.foundry.proxy.CommonFoundryProxy;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemRevolver extends Item
{
  public IIcon icon;
  
  public ItemRevolver()
  {
    super();
    setCreativeTab(FoundryTabFluids.tab);
    setMaxStackSize(1);
    setUnlocalizedName("revolver");
    setHasSubtypes(true);

    MinecraftForge.EVENT_BUS.register(this);
  }


  @Override
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister register)
  {
    icon = register.registerIcon("foundry:revolver");
  }
  

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIconFromDamage(int dmg)
  {
    return icon;
  }
  


  private MovingObjectPosition Trace(World world, EntityPlayer player)
  {
    float pitch = -player.rotationPitch;
    float yaw = -player.rotationYaw;
    Vec3 start = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight() - 0.1, player.posZ);
    float cyaw = MathHelper.cos(yaw * 0.017453292F - (float) Math.PI);
    float syaw = MathHelper.sin(yaw * 0.017453292F - (float) Math.PI);
    float cpitch = -MathHelper.cos(pitch * 0.017453292F);
    
    double distance = 100.0D;
    
    double dx = syaw * cpitch * distance;
    double dy = MathHelper.sin(pitch * 0.017453292F) * distance;
    double dz = cyaw * cpitch * distance;
    Vec3 end = start.addVector(dx,dy,dz);

    Vec3 tstart = Vec3.createVectorHelper(start.xCoord, start.yCoord, start.zCoord);
    Vec3 tend = Vec3.createVectorHelper(end.xCoord, end.yCoord, end.zCoord);
    MovingObjectPosition obj = world.func_147447_a(tstart, tend, true, false, false);
    
    
    @SuppressWarnings("unchecked")
    List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(100, 100, 100));
    double min_dist = obj != null?obj.hitVec.distanceTo(start):100;
    for(Entity ent:entities)
    {
      if(ent.canBeCollidedWith() && ent.boundingBox != null)
      {
        MovingObjectPosition ent_obj = ent.boundingBox.expand(0.3, 0.3, 0.3).calculateIntercept(start, end);
        if(ent_obj != null)
        {
          if(ent_obj.typeOfHit == MovingObjectType.BLOCK)
          {
            ent_obj.typeOfHit = MovingObjectType.ENTITY;
            ent_obj.entityHit = ent;
          }
          double d = ent_obj.hitVec.distanceTo(start);
          if(obj == null || d < min_dist)
          {
            min_dist = d;
            
            obj = ent_obj;
          }
        }
      }
    }
    if(obj != null)
    {
      obj.hitInfo = start;
    }
    return obj;
  }

  @Override
  public boolean getShareTag()
  {
    return true;
  }

  
  @Override
  public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
  {
    if(player.isSneaking())
    {
      if (!world.isRemote)
      {
        player.openGui(ModFoundry.instance, CommonFoundryProxy.GUI_REVOLVER, world, 0, 0, 0);
      }
    } else
    {
      int position = stack.getTagCompound().getInteger("position");
      NBTTagCompound tag = stack.getTagCompound().getCompoundTag("Slot_" + position);
      if(!tag.getBoolean("Empty"))
      {
        ItemStack ammo_item = ItemStack.loadItemStackFromNBT(tag);
        if(!world.isRemote)
        {
          MovingObjectPosition obj = Trace(world, player);
          IFirearmAmmo ammo = (IFirearmAmmo) ammo_item.getItem();
          if(obj != null)
          {
            switch(obj.typeOfHit)
            {
              case BLOCK:
                ammo.OnHitBlock(ammo_item, player, (Vec3) obj.hitInfo, world, obj.blockX, obj.blockY, obj.blockZ, ForgeDirection.getOrientation(obj.sideHit));
                break;
              case ENTITY:
                ammo.OnHitEntity(ammo_item, player, (Vec3) obj.hitInfo, obj.entityHit);
                break;
              default:
                break;
            }
          }
        }
        tag.setBoolean("Empty", true);
      }
      stack.getTagCompound().setInteger("position", (position + 1) % 8);
    }
    return stack;
  }
  
  @Override
  public int getItemStackLimit(ItemStack stack)
  {
    return 1;
  }

  @SuppressWarnings("unchecked")
  @Override
  @SideOnly(Side.CLIENT)
  public void getSubItems(Item item,CreativeTabs tabs, @SuppressWarnings("rawtypes") List list)
  {
    list.add(Empty());
    list.add(Loaded());
  }

  @SuppressWarnings("unchecked")
  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, EntityPlayer player, @SuppressWarnings("rawtypes") List list, boolean par4)
  {
    if(/*player.isSneaking()*/true)
    {
      int position = stack.getTagCompound().getInteger("position");
      int i;
      for(i = 0; i < 8; i++)
      {
        int j = (i + position) % 8;
        NBTTagCompound tag = stack.getTagCompound().getCompoundTag("Slot_" + j);
        if(tag.getBoolean("Empty"))
        {
          list.add(EnumChatFormatting.BLUE + "< Empty >");
        } else
        {
          ItemStack ammo = ItemStack.loadItemStackFromNBT(tag);
          list.add(EnumChatFormatting.BLUE + ammo.getDisplayName());
        }
      }
    }
  }

  public void SetAmmo(ItemStack stack,int slot,ItemStack ammo)
  {
    if(stack.getItem() != this)
    {
      throw new IllegalArgumentException("Stack is not a revolver");
    }
    if(slot < 0 || slot > 7)
    {
      throw new IllegalArgumentException("Slot index not in range: " + slot);
    }
    NBTTagCompound tag = new NBTTagCompound();
    if(ammo == null)
    {
      tag.setBoolean("Empty", true);
    } else
    {
      tag.setBoolean("Empty", false);
      ammo.writeToNBT(tag);
    }
    stack.getTagCompound().setTag("Slot_" + slot,tag);
  }
  

  public ItemStack GetAmmo(ItemStack stack,int slot)
  {
    if(stack.getItem() != this)
    {
      throw new IllegalArgumentException("Stack is not a revolver");
    }
    if(slot < 0 || slot > 7)
    {
      throw new IllegalArgumentException("Slot index not in range: " + slot);
    }
    NBTTagCompound tag = stack.getTagCompound().getCompoundTag("Slot_" + slot);
    if(tag.getBoolean("Empty"))
    {
      return null;
    } else
    {
      return ItemStack.loadItemStackFromNBT(tag);
    }
  }

  public ItemStack Empty()
  {
    ItemStack stack = new ItemStack(this);
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("position", 0);
    int i;
    for(i = 0; i < 8; i++)
    {
      NBTTagCompound slot = new NBTTagCompound();
      slot.setBoolean("Empty",true);
      nbt.setTag("Slot_" + i,slot);
    }
    stack.setTagCompound(nbt);
    return stack;
  }

  public ItemStack Loaded()
  {
    ItemStack stack = new ItemStack(this);
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("position", 0);
    int i;
    ItemStack ammo = new ItemStack(FoundryItems.item_ammo);
    for(i = 0; i < 8; i++)
    {
      NBTTagCompound slot = new NBTTagCompound();
      slot.setBoolean("Empty",false);
      ammo.writeToNBT(slot);
      nbt.setTag("Slot_" + i,slot);
    }
    stack.setTagCompound(nbt);
    return stack;
  }
}