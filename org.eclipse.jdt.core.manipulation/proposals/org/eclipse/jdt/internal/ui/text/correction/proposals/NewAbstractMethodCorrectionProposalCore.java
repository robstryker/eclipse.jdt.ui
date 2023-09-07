package org.eclipse.jdt.internal.ui.text.correction.proposals;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;

public class NewAbstractMethodCorrectionProposalCore extends NewMethodCorrectionProposalCore {
	public NewAbstractMethodCorrectionProposalCore(String label, ICompilationUnit targetCU, ASTNode invocationNode, List<Expression> arguments, ITypeBinding binding, int relevance) {
		super(label, targetCU, invocationNode, arguments, binding, relevance);
	}

	@Override
	protected int evaluateModifiers(ASTNode targetTypeDecl) {
		return Modifier.ABSTRACT | Modifier.PROTECTED;
	}
}