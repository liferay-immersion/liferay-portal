/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.searcher;

import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.tuning.rankings.web.internal.BaseRankingsWebTestCase;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.searcher.helper.RankingSearchRequestHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingSearchRequestContributorTest
	extends BaseRankingsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_rankingSearchRequestContributor, "rankingIndexNameBuilder",
			rankingIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_rankingSearchRequestContributor, "rankingIndexReader",
			_rankingIndexReader);
		ReflectionTestUtil.setFieldValue(
			_rankingSearchRequestContributor, "rankingSearchRequestHelper",
			_rankingSearchRequestHelper);
		ReflectionTestUtil.setFieldValue(
			_rankingSearchRequestContributor, "searchEngineHelper",
			_searchEngineHelper);
		ReflectionTestUtil.setFieldValue(
			_rankingSearchRequestContributor, "searchRequestBuilderFactory",
			searchRequestBuilderFactory);
	}

	@Test
	public void testContributeIsSearchEngineSolrTrue() {
		SearchEngine searchEngine = Mockito.mock(SearchEngine.class);

		Mockito.doReturn(
			"Solr"
		).when(
			searchEngine
		).getVendor();

		Mockito.doReturn(
			searchEngine
		).when(
			_searchEngineHelper
		).getSearchEngine();

		SearchRequest searchRequest = Mockito.mock(SearchRequest.class);

		Assert.assertEquals(
			searchRequest,
			_rankingSearchRequestContributor.contribute(searchRequest));
	}

	@Test
	public void testContributeRankingIndexReaderIsExistsFalse() {
		_setUpContributorMocks(false);

		SearchRequest searchRequest = Mockito.mock(SearchRequest.class);

		Assert.assertEquals(
			searchRequest,
			_rankingSearchRequestContributor.contribute(searchRequest));
	}

	@Test
	public void testContributeRankingIndexReaderIsExistsTrue() {
		SearchRequestBuilder searchRequestBuilder = _setUpContributorMocks(
			true);

		Mockito.doReturn(
			Mockito.mock(Ranking.class)
		).when(
			_rankingIndexReader
		).fetchByQueryString(
			Mockito.any(), Mockito.anyString()
		);

		Mockito.doNothing(
		).when(
			_rankingSearchRequestHelper
		).contribute(
			Mockito.any(), Mockito.any()
		);

		SearchRequest searchRequest = Mockito.mock(SearchRequest.class);

		Mockito.doReturn(
			searchRequest
		).when(
			searchRequestBuilder
		).build();

		Assert.assertEquals(
			searchRequest,
			_rankingSearchRequestContributor.contribute(searchRequest));
	}

	@SuppressWarnings("unchecked")
	private SearchRequestBuilder _setUpContributorMocks(
		boolean rankingIndexNameExist) {

		SearchRequestBuilder searchRequestBuilder = Mockito.mock(
			SearchRequestBuilder.class);

		Mockito.doReturn(
			searchRequestBuilder
		).when(
			searchRequestBuilder
		).withSearchContext(
			Mockito.any(Consumer.class)
		);

		setUpRankingIndexNameBuilder();
		_setUpSearchEngineHelper();
		setUpSearchRequestBuilderFactory(searchRequestBuilder);

		Mockito.doReturn(
			rankingIndexNameExist
		).when(
			_rankingIndexReader
		).isExists(
			Mockito.any()
		);

		return searchRequestBuilder;
	}

	private void _setUpSearchEngineHelper() {
		SearchEngine searchEngine = Mockito.mock(SearchEngine.class);

		Mockito.doReturn(
			"Elasticsearch"
		).when(
			searchEngine
		).getVendor();

		Mockito.doReturn(
			searchEngine
		).when(
			_searchEngineHelper
		).getSearchEngine();
	}

	private final RankingIndexReader _rankingIndexReader = Mockito.mock(
		RankingIndexReader.class);
	private final RankingSearchRequestContributor
		_rankingSearchRequestContributor =
			new RankingSearchRequestContributor();
	private final RankingSearchRequestHelper _rankingSearchRequestHelper =
		Mockito.mock(RankingSearchRequestHelper.class);
	private final SearchEngineHelper _searchEngineHelper = Mockito.mock(
		SearchEngineHelper.class);

}