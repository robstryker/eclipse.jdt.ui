package org.eclipse.jdt.internal.ui.text.correction.proposals;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ImportRewrite;

import org.eclipse.jdt.internal.corext.refactoring.structure.ImportRemover;

import org.eclipse.jdt.ui.text.java.correction.ASTRewriteCorrectionProposalCore;

public class ASTRewriteRemoveImportsCorrectionProposalCore extends ASTRewriteCorrectionProposalCore {
	private ImportRemover fImportRemover;

	public ASTRewriteRemoveImportsCorrectionProposalCore(String name, ICompilationUnit cu, ASTRewrite rewrite, int relevance) {
		super(name, cu, rewrite, relevance);
	}
	public void setImportRemover(ImportRemover remover) {
		fImportRemover= remover;
	}

	@Override
	protected ASTRewrite getRewrite() throws CoreException {
		ASTRewrite rewrite= super.getRewrite();
		ImportRewrite importRewrite= getImportRewrite();
		if (fImportRemover != null && importRewrite != null) {
			fImportRemover.applyRemoves(importRewrite);
		}
		return rewrite;
	}
}