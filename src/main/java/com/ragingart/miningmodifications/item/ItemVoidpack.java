package com.ragingart.miningmodifications.item;

import cofh.api.item.IInventoryContainerItem;
import com.ragingart.miningmodifications.MiningModifications;
import com.ragingart.miningmodifications.container.ContainerVoidpack;
import com.ragingart.miningmodifications.generics.ItemMM;
import com.ragingart.miningmodifications.ref.Gui;
import com.ragingart.miningmodifications.ref.Names;
import com.ragingart.miningmodifications.util.NBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

/**
 * Created by MaaT on 12.10.2014.
 */
public class ItemVoidpack extends ItemMM implements IInventoryContainerItem{

    public ItemVoidpack() {
        super(Names.Items.VOIDPACK);
    }

    private int tick=0;

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int i, boolean b) {
        if(entity instanceof EntityPlayer) {
            if (tick % 20 == 0 && isState(itemStack)) {
                Item[] toErase = new Item[5];
                int[] meErase = new int[5];
                if (itemStack.hasTagCompound() && !itemStack.stackTagCompound.hasNoTags()) {
                    for (int j = 0; j < 5; j++) {
                        if (itemStack.stackTagCompound.hasKey("Slot" + j)) {
                            ItemStack aStack = ItemStack.loadItemStackFromNBT(itemStack.stackTagCompound.getCompoundTag("Slot" + j));
                            if (aStack != null) {
                                toErase[j] = aStack.getItem();
                                if(isMetaState(itemStack,j)) {
                                    meErase[j] = aStack.getItemDamage();
                                }else{
                                    meErase[j] = -1;
                                }
                            }
                        }
                    }
                }
                ContainerVoidpack.doErase(toErase, meErase, ((EntityPlayer) entity).inventory);
                tick = 0;
            }
            tick++;
        }
    }

    public static boolean isState(ItemStack stack) {
        return NBTHelper.hasTag(stack,"state") && NBTHelper.getBoolean(stack, "state");
    }

    public static void setState(boolean state,ItemStack stack) {
        NBTHelper.setBoolean(stack,"state",state);
    }


    public static boolean isMetaState(ItemStack stack, int n) {
        return NBTHelper.hasTag(stack,"meta"+n) && NBTHelper.getBoolean(stack, "meta"+n);
    }

    public static void setMetaState(boolean state,ItemStack stack,int n) {
        NBTHelper.setBoolean(stack,"meta"+n,state);
    }

    public static void toggleMetaState(ItemStack aStack,int i){
        setMetaState(!isMetaState(aStack,i),aStack,i);
    }



    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        if(entityPlayer.isSneaking()){
            setState(!isState(itemStack),itemStack);
            if(!world.isRemote) {
                entityPlayer.addChatMessage(new ChatComponentText(isState(itemStack)? "Activated":"Deactivated"));
            }
        }else {
            entityPlayer.openGui(MiningModifications.instance, Gui.ID.VOIDPACK.ordinal(), entityPlayer.worldObj, (int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
        }
        return itemStack;
    }

    @Override
    public int getSizeInventory(ItemStack container) {
        return 5;
    }
}