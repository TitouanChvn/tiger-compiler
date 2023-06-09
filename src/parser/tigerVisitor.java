// Generated from ./tiger.g4 by ANTLR 4.9.2

package parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link tigerParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface tigerVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code Integer}
	 * labeled alternative in {@link tigerParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(tigerParser.IntegerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code String}
	 * labeled alternative in {@link tigerParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(tigerParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SeqExpAtom}
	 * labeled alternative in {@link tigerParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeqExpAtom(tigerParser.SeqExpAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallExpAtom}
	 * labeled alternative in {@link tigerParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExpAtom(tigerParser.CallExpAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdAtom}
	 * labeled alternative in {@link tigerParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdAtom(tigerParser.IdAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LValueAtom}
	 * labeled alternative in {@link tigerParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLValueAtom(tigerParser.LValueAtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(tigerParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#dec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDec(tigerParser.DecContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#typeDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDec(tigerParser.TypeDecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdTy}
	 * labeled alternative in {@link tigerParser#ty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdTy(tigerParser.IdTyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrTyTy}
	 * labeled alternative in {@link tigerParser#ty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrTyTy(tigerParser.ArrTyTyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RecTyTy}
	 * labeled alternative in {@link tigerParser#ty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecTyTy(tigerParser.RecTyTyContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#arrTy}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrTy(tigerParser.ArrTyContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#recTy}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecTy(tigerParser.RecTyContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#listrecTyFieldDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListrecTyFieldDec(tigerParser.ListrecTyFieldDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#fieldDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDec(tigerParser.FieldDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#funDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunDec(tigerParser.FunDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#listArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListArgs(tigerParser.ListArgsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funDecBodyWithoutId}
	 * labeled alternative in {@link tigerParser#funDecBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunDecBodyWithoutId(tigerParser.FunDecBodyWithoutIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funDecBodyWithId}
	 * labeled alternative in {@link tigerParser#funDecBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunDecBodyWithId(tigerParser.FunDecBodyWithIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#varDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDec(tigerParser.VarDecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarDecExp}
	 * labeled alternative in {@link tigerParser#varDecA}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecExp(tigerParser.VarDecExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarDecIdExp}
	 * labeled alternative in {@link tigerParser#varDecA}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecIdExp(tigerParser.VarDecIdExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#lValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLValue(tigerParser.LValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Nil}
	 * labeled alternative in {@link tigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNil(tigerParser.NilContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Expcreate}
	 * labeled alternative in {@link tigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpcreate(tigerParser.ExpcreateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpinfixExp}
	 * labeled alternative in {@link tigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpinfixExp(tigerParser.ExpinfixExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Expassignment}
	 * labeled alternative in {@link tigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpassignment(tigerParser.ExpassignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpIfThenElse}
	 * labeled alternative in {@link tigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpIfThenElse(tigerParser.ExpIfThenElseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpIfThen}
	 * labeled alternative in {@link tigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpIfThen(tigerParser.ExpIfThenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpwhileExp}
	 * labeled alternative in {@link tigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpwhileExp(tigerParser.ExpwhileExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpforExp}
	 * labeled alternative in {@link tigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpforExp(tigerParser.ExpforExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Break}
	 * labeled alternative in {@link tigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak(tigerParser.BreakContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpletExp}
	 * labeled alternative in {@link tigerParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpletExp(tigerParser.ExpletExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#seqExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeqExp(tigerParser.SeqExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#callExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExp(tigerParser.CallExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#callExpList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExpList(tigerParser.CallExpListContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#create}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreate(tigerParser.CreateContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#createA}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateA(tigerParser.CreateAContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#arrCreate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrCreate(tigerParser.ArrCreateContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#recCreate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecCreate(tigerParser.RecCreateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Affect}
	 * labeled alternative in {@link tigerParser#fieldCreate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAffect(tigerParser.AffectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Assign}
	 * labeled alternative in {@link tigerParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(tigerParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#ifThenElse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElse(tigerParser.IfThenElseContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#ifThen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThen(tigerParser.IfThenContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#whileExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileExp(tigerParser.WhileExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#forExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForExp(tigerParser.ForExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#letExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetExp(tigerParser.LetExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#listDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListDec(tigerParser.ListDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#listExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListExp(tigerParser.ListExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#orExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExp(tigerParser.OrExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#andExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExp(tigerParser.AndExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#cmpExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmpExp(tigerParser.CmpExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#asExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsExp(tigerParser.AsExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link tigerParser#mdExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMdExp(tigerParser.MdExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Negation}
	 * labeled alternative in {@link tigerParser#negExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegation(tigerParser.NegationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AtomN}
	 * labeled alternative in {@link tigerParser#negExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomN(tigerParser.AtomNContext ctx);
}