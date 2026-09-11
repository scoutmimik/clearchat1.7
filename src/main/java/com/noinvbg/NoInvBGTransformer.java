package com.noinvbg;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class NoInvBGTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null) {
            return null;
        }

        if ("net.minecraft.client.gui.inventory.GuiContainer".equals(transformedName)) {
            ClassReader reader = new ClassReader(basicClass);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);

            for (MethodNode method : node.methods) {
                if ("(IIF)V".equals(method.desc)) {
                    InsnList newInstructions = new InsnList();

                    for (AbstractInsnNode insn : method.instructions.toArray()) {
                        if (insn instanceof MethodInsnNode) {
                            MethodInsnNode mInsn = (MethodInsnNode) insn;
                            if ("drawDefaultBackground".equals(mInsn.name) || "func_146269_k".equals(mInsn.name)) {
                                newInstructions.add(new MethodInsnNode(
                                    Opcodes.INVOKESTATIC,
                                    "com/noinvbg/NoInvBGTransformer",
                                    "redirectDrawBackground",
                                    "(Lnet/minecraft/client/gui/inventory/GuiContainer;)V",
                                    false
                                ));
                                continue;
                            }
                        }
                        newInstructions.add(insn);
                    }
                    method.instructions = newInstructions;
                }
            }

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            node.accept(writer);
            return writer.toByteArray();
        }

        return basicClass;
    }

    public static void redirectDrawBackground(net.minecraft.client.gui.inventory.GuiContainer gui) {
        if (!Main.noInvBackground) {
            gui.drawDefaultBackground();
        }
    }
}
