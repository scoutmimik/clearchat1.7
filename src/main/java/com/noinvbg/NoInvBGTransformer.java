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

        if (transformedName.equals("net.minecraft.client.gui.inventory.GuiContainer")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);

            for (MethodNode method : node.methods) {
                // Hľadáme metódu drawScreen (desc: (IIF)V)
                if (method.desc.equals("(IIF)V")) {
                    InsnList newInstructions = new InsnList();

                    for (AbstractInsnNode insn : method.instructions.toArray()) {
                        if (insn instanceof MethodInsnNode) {
                            MethodInsnNode mInsn = (MethodInsnNode) insn;
                            // Odchytenie volania drawDefaultBackground (Obf: func_146269_k / Deobf: drawDefaultBackground)
                            if (mInsn.name.equals("drawDefaultBackground") || mInsn.name.equals("func_146269_k")) {
                                // Nahradenie volaním podmienenej metódy
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
