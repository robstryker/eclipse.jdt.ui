package org.eclipse.jdt.internal.ui.text.correction.proposals;

import java.util.List;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import org.eclipse.jdt.internal.corext.dom.LinkedNodeFinder;

import org.eclipse.jdt.ui.text.java.IInvocationContext;
import org.eclipse.jdt.ui.text.java.correction.ASTRewriteCorrectionProposalCore;

public class CorrectMainTypeNameProposalCore extends ASTRewriteCorrectionProposalCore {

	private final String fOldName;
	private final String fNewName;
	private final IInvocationContext fContext;

	public CorrectMainTypeNameProposalCore(String name, ICompilationUnit cu, ASTRewrite rewrite, IInvocationContext context, String oldTypeName, String newTypeName, int relevance) {
		super(name, cu, rewrite, relevance);
		fContext= context;
		fOldName= oldTypeName;
		fNewName= newTypeName;
	}


	@Override
	protected ASTRewrite getRewrite() throws CoreException {
		CompilationUnit astRoot= fContext.getASTRoot();

		AST ast= astRoot.getAST();
		ASTRewrite rewrite= ASTRewrite.create(ast);

		AbstractTypeDeclaration decl= findTypeDeclaration(astRoot.types(), fOldName);
		if (decl != null) {
			for (ASTNode sameNode : LinkedNodeFinder.findByNode(astRoot, decl.getName())) {
				rewrite.replace(sameNode, ast.newSimpleName(fNewName), null);
			}
		}
		return rewrite;
	}

	private AbstractTypeDeclaration findTypeDeclaration(List<AbstractTypeDeclaration> types, String name) {
		for (AbstractTypeDeclaration decl : types) {
			if (name.equals(decl.getName().getIdentifier())) {
				return decl;
			}
		}
		return null;
	}

}