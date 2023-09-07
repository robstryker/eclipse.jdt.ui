package org.eclipse.jdt.internal.ui.text.correction.proposals;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

public class QualifyTypeProposalCore extends LinkedCorrectionProposalCore {
	private final SimpleName fOriginalName;
	private final String fNewQualifiedName;

	/**
	 * Creates a qualified name correction proposal.
	 *
	 * @param label the display name of the proposal
	 * @param targetCU the compilation unit that is modified
	 * @param relevance the relevance of this proposal
	 * @param original the name to replace
	 * @param qualifiedName the fully qualified name to use
	 */
	public QualifyTypeProposalCore(String label, ICompilationUnit targetCU, int relevance, SimpleName original, String qualifiedName) {
		super(label, targetCU, null, relevance);
		fOriginalName= original;
		fNewQualifiedName= qualifiedName;
	}


	@Override
	protected ASTRewrite getRewrite() throws CoreException {
		AST ast= fOriginalName.getAST();
		ASTRewrite rewrite= ASTRewrite.create(ast);

		Name newName= ast.newName(fNewQualifiedName);
		rewrite.replace(fOriginalName, newName, null);

		return rewrite;
	}

}