/**
 * <copyright>
 *
 * Copyright (c) 2014 http://www.big.tuwien.ac.at All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * </copyright>
 */
package swt.wt.lab2.api.impl;

import java.io.InputStream;

import javax.servlet.ServletContext;

import swt.wt.lab2.api.QuestionDataProvider;
import swt.wt.lab2.api.QuizFactory;

public class ServletQuizFactory extends SimpleQuizFactory {

	private ServletContext context;

	public static QuizFactory init(ServletContext context) {
		return new ServletQuizFactory(context);
	}

	public ServletQuizFactory(ServletContext context) {
		this.context = context;
	}

	@Override
	public QuestionDataProvider createQuestionDataProvider() {
		InputStream inputStream = context
				.getResourceAsStream("/WEB-INF/data.json");
		return new JSONQuestionDataProvider(inputStream, this);
	}
}
