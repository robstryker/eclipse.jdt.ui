package org.eclipse.jdt.internal.ui.text.correction.proposals;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import org.eclipse.jdt.internal.corext.fix.CompilationUnitRewriteOperationsFixCore.CompilationUnitRewriteOperation;
import org.eclipse.jdt.internal.corext.fix.MissingAnnotationAttributesProposalOperation;
import org.eclipse.jdt.internal.corext.refactoring.structure.CompilationUnitRewrite;

import org.eclipse.jdt.internal.ui.text.correction.CorrectionMessages;

public class MissingAnnotationAttributesProposalCore extends LinkedCorrectionProposalCore {

	private CompilationUnitRewrite fCompilationUnitRewrite;
	private CompilationUnitRewriteOperation fASTRewriteProposalCore;

	public MissingAnnotationAttributesProposalCore(ICompilationUnit cu, Annotation annotation, int relevance) {
		super(CorrectionMessages.MissingAnnotationAttributesProposal_add_missing_attributes_label, cu, null, relevance);
		fASTRewriteProposalCore= new MissingAnnotationAttributesProposalOperation(annotation);
		fCompilationUnitRewrite= new CompilationUnitRewrite(cu, (CompilationUnit)annotation.getRoot());
	}

	@Override
	protected ASTRewrite getRewrite() throws CoreException {
		fASTRewriteProposalCore.rewriteAST(fCompilationUnitRewrite, getLinkedProposalModel());
		return fCompilationUnitRewrite.getASTRewrite();
	}
}