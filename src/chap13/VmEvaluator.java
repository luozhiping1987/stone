package chap13;
import java.util.List;
import stone.StoneException;
import stone.词类;
import chap11.环境优化器类;
import chap6.Environment;
import chap6.基本求值器类.ASTreeEx;
import chap7.函数求值器类;
import javassist.gluonj.*;
import static chap13.Opcode.*;
import static javassist.gluonj.GluonJ.revise;
import stone.ast.*;

@Require(环境优化器类.class)
@Reviser public class VmEvaluator {
    @Reviser public static interface EnvEx3 extends 环境优化器类.EnvEx2 {
        StoneVM stoneVM();
        Code code();
    }
    @Reviser public static abstract class ASTreeVmEx extends 语法树类 {
        public void compile(Code c) {}
    }
    @Reviser public static class ASTListEx extends ASTList {
        public ASTListEx(List<语法树类> c) { super(c); }
        public void compile(Code c) {
            for (语法树类 t: this)
                ((ASTreeVmEx)t).compile(c);
        }
    }
    @Reviser public static class DefStmntVmEx extends 环境优化器类.DefStmntEx {
        public DefStmntVmEx(List<语法树类> c) { super(c); }
        @Override public Object eval(Environment env) {
            String funcName = name();
            EnvEx3 vmenv = (EnvEx3)env;
            Code code = vmenv.code();
            int entry = code.position();
            compile(code);
            ((EnvEx3)env).putNew(funcName, new VmFunction(parameters(), body(),
                                                          env, entry));
            return funcName;
        }
        public void compile(Code c) {
            c.nextReg = 0;
            c.frameSize = size + StoneVM.SAVE_AREA_SIZE;
            c.add(SAVE);
            c.add(encodeOffset(size));
            ((ASTreeVmEx)revise(body())).compile(c);
            c.add(MOVE);
            c.add(encodeRegister(c.nextReg - 1));
            c.add(encodeOffset(0));
            c.add(RESTORE);
            c.add(encodeOffset(size));
            c.add(RETURN);
        }
    }
    @Reviser public static class ParamsEx2 extends 环境优化器类.ParamsEx {
        public ParamsEx2(List<语法树类> c) { super(c); }
        @Override public void eval(Environment env, int index, Object value) {
            StoneVM vm = ((EnvEx3)env).stoneVM();
            vm.stack()[offsets[index]] = value;
        }
    }
    @Reviser public static class NumberEx extends NumberLiteral {
        public NumberEx(词类 t) { super(t); }
        public void compile(Code c) {
            int v = value();
            if (Byte.MIN_VALUE <= v && v <= Byte.MAX_VALUE) {
                c.add(BCONST);
                c.add((byte)v);
            }
            else {
                c.add(ICONST);
                c.add(v);
            }
            c.add(encodeRegister(c.nextReg++));
        }
    }
    @Reviser public static class StringEx extends StringLiteral {
        public StringEx(词类 t) { super(t); }
        public void compile(Code c) {
            int i = c.record(value());
            c.add(SCONST);
            c.add(encodeShortOffset(i));
            c.add(encodeRegister(c.nextReg++));
        }
    }
    @Reviser public static class NameEx2 extends 环境优化器类.NameEx {
        public NameEx2(词类 t) { super(t); }
        public void compile(Code c) {
            if (nest > 0) {
                c.add(GMOVE);
                c.add(encodeShortOffset(index));
                c.add(encodeRegister(c.nextReg++));
            }
            else {
                c.add(MOVE);
                c.add(encodeOffset(index));
                c.add(encodeRegister(c.nextReg++));
            }
        }
        public void compileAssign(Code c) {
            if (nest > 0) {
                c.add(GMOVE);
                c.add(encodeRegister(c.nextReg - 1));
                c.add(encodeShortOffset(index));
            }
            else {
                c.add(MOVE);
                c.add(encodeRegister(c.nextReg - 1));
                c.add(encodeOffset(index));
            }
        }
    }
    @Reviser public static class NegativeEx extends NegativeExpr {
        public NegativeEx(List<语法树类> c) { super(c); }
        public void compile(Code c) {
            ((ASTreeVmEx)operand()).compile(c);
            c.add(NEG);
            c.add(encodeRegister(c.nextReg - 1));   
        }
    }
    @Reviser public static class BinaryEx extends 二元表达式 {
        public BinaryEx(List<语法树类> c) { super(c); }
        public void compile(Code c) {
            String op = operator();
            if (op.equals("=")) {
                语法树类 l = left();
                if (l instanceof Name) {
                    ((ASTreeVmEx)right()).compile(c);
                    ((NameEx2)l).compileAssign(c);
                }
                else
                    throw new StoneException("bad assignment", this);
            }
            else {
                ((ASTreeVmEx)left()).compile(c);
                ((ASTreeVmEx)right()).compile(c);
                c.add(getOpcode(op));
                c.add(encodeRegister(c.nextReg - 2));
                c.add(encodeRegister(c.nextReg - 1));
                c.nextReg--;
            }
        }
        protected byte getOpcode(String op) {
            if (op.equals("+"))
                return ADD;
            else if (op.equals("-"))
                return SUB;
            else if (op.equals("*"))
                return MUL;
            else if (op.equals("/"))
                return DIV;
            else if (op.equals("%"))
                return REM;
            else if (op.equals("=="))
                return EQUAL;
            else if (op.equals(">"))
                return MORE;
            else if (op.equals("<"))
                return LESS;
            else
                throw new StoneException("bad operator", this);
        }
    }
    @Reviser public static class PrimaryVmEx extends 函数求值器类.PrimaryEx {
        public PrimaryVmEx(List<语法树类> c) { super(c); }
        public void compile(Code c) {
            compileSubExpr(c, 0);
        }
        public void compileSubExpr(Code c, int nest) {
            if (hasPostfix(nest)) {
                compileSubExpr(c, nest + 1);
                ((ASTreeVmEx)revise(postfix(nest))).compile(c);
            }
            else
                ((ASTreeVmEx)operand()).compile(c);
        }
    }
    @Reviser public static class ArgumentsEx extends Arguments {
        public ArgumentsEx(List<语法树类> c) { super(c); }
        public void compile(Code c) {
            int newOffset = c.frameSize;
            int numOfArgs = 0;
            for (语法树类 a: this) {
                ((ASTreeVmEx)a).compile(c);
                c.add(MOVE);
                c.add(encodeRegister(--c.nextReg));
                c.add(encodeOffset(newOffset++));
                numOfArgs++;
            }
            c.add(CALL);
            c.add(encodeRegister(--c.nextReg));
            c.add(encodeOffset(numOfArgs));
            c.add(MOVE);
            c.add(encodeOffset(c.frameSize));
            c.add(encodeRegister(c.nextReg++));
        }
        public Object eval(Environment env, Object value) {
            if (!(value instanceof VmFunction))
                throw new StoneException("bad function", this);
            VmFunction func = (VmFunction)value;
            ParameterList params = func.parameters();
            if (size() != params.size())
                throw new StoneException("bad number of arguments", this);
            int num = 0;
            for (语法树类 a: this)
                ((ParamsEx2)params).eval(env, num++, ((ASTreeEx)a).eval(env)); 
            StoneVM svm = ((EnvEx3)env).stoneVM();
            svm.run(func.entry());
            return svm.stack()[0];
        }
    }
    @Reviser public static class BlockEx extends BlockStmnt {
        public BlockEx(List<语法树类> c) { super(c); }
        public void compile(Code c) {
            if (this.子个数() > 0) {
                int initReg = c.nextReg;
                for (语法树类 a: this) {
                    c.nextReg = initReg;
                    ((ASTreeVmEx)a).compile(c);
                }
            }
            else {
                c.add(BCONST);
                c.add((byte)0);
                c.add(encodeRegister(c.nextReg++));
            }
        }
    }
    @Reviser public static class IfEx extends IfStmnt {
        public IfEx(List<语法树类> c) { super(c); }
        public void compile(Code c) {
            ((ASTreeVmEx)condition()).compile(c);
            int pos = c.position();
            c.add(IFZERO);
            c.add(encodeRegister(--c.nextReg));
            c.add(encodeShortOffset(0));
            int oldReg = c.nextReg;
            ((ASTreeVmEx)thenBlock()).compile(c);
            int pos2 = c.position();
            c.add(GOTO);
            c.add(encodeShortOffset(0));
            c.set(encodeShortOffset(c.position() - pos), pos + 2);
            语法树类 b = elseBlock();
            c.nextReg = oldReg;
            if (b != null)
                ((ASTreeVmEx)b).compile(c);
            else {
                c.add(BCONST);
                c.add((byte)0);
                c.add(encodeRegister(c.nextReg++));
            }
            c.set(encodeShortOffset(c.position() - pos2), pos2 + 1);
        }
    }
    @Reviser public static class WhileEx extends While声明 {
        public WhileEx(List<语法树类> c) { super(c); }
        public void compile(Code c) {
            int oldReg = c.nextReg;
            c.add(BCONST);
            c.add((byte)0);
            c.add(encodeRegister(c.nextReg++));
            int pos = c.position();
            ((ASTreeVmEx)condition()).compile(c);
            int pos2 = c.position();
            c.add(IFZERO);
            c.add(encodeRegister(--c.nextReg));
            c.add(encodeShortOffset(0));
            c.nextReg = oldReg;
            ((ASTreeVmEx)body()).compile(c);
            int pos3= c.position();
            c.add(GOTO);
            c.add(encodeShortOffset(pos - pos3));
            c.set(encodeShortOffset(c.position() - pos2), pos2 + 2);
        }
    }
}
