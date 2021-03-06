package com.ragingart.miningmodifications.network.messages;

import com.ragingart.miningmodifications.item.ItemVoidpack;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * Created by MaaT on 14.11.2014.
 */
public class MessageButtonClick implements IMessage,IMessageHandler<MessageButtonClick,IMessage> {
    public int containerIndex;
    public int slot;

    public MessageButtonClick(){

    }

    public MessageButtonClick(int c, int s){
        this.containerIndex = c;
        this.slot = s;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        containerIndex = buf.readInt();
        slot = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(containerIndex);
        buf.writeInt(slot);
    }

    @Override
    public IMessage onMessage(MessageButtonClick message, MessageContext ctx) {
        ItemVoidpack.toggleMetaState(ctx.getServerHandler().playerEntity.inventory.mainInventory[message.containerIndex],message.slot);
        return null;
    }

}
