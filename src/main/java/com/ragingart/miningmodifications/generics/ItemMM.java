package com.ragingart.miningmodifications.generics;

import com.ragingart.miningmodifications.creativetab.CreativeTabMM;
import com.ragingart.miningmodifications.ref.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.lwjgl.input.Keyboard;

import java.util.Collections;
import java.util.List;

public abstract class ItemMM extends Item
{
    public String myName;

    public ItemMM(String aName){
        super();
        this.myName = aName;
        this.setCreativeTab(CreativeTabMM.MM_TAB);
        this.setUnlocalizedName(myName);
        GameRegistry.registerItem(this,getName());
    }

    public String getName(){
        return myName;
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Names.MOD_PREFIX, getName());
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", Names.MOD_PREFIX, getName());
    }


    public void addSpecialInfo(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean b){
        String info = StatCollector.translateToLocal(Names.INFO_PREFIX + Names.getUnwrappedUnlocalizedName(getUnlocalizedName()));
        Collections.addAll(list, info.split("%"));
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean b) {
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            list.add(EnumChatFormatting.WHITE + "Release " + EnumChatFormatting.RED + "Shift" + EnumChatFormatting.WHITE + " for less Information");
            list.add(EnumChatFormatting.GREEN + "" + EnumChatFormatting.ITALIC + "Info:");
            addSpecialInfo(itemStack,entityPlayer,list,b);
        }else{
            list.add(EnumChatFormatting.WHITE + "Hold " + EnumChatFormatting.GREEN + "Shift" + EnumChatFormatting.WHITE + " for more Information");
        }

    }
}
