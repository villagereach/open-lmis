/*
 * This program was produced for the U.S. Agency for International Development. It was prepared by the USAID | DELIVER PROJECT, Task Order 4. It is part of a project which utilizes code originally licensed under the terms of the Mozilla Public License (MPL) v2 and therefore is licensed under MPL v2 or later.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the Mozilla Public License as published by the Mozilla Foundation, either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Mozilla Public License for more details.
 *
 * You should have received a copy of the Mozilla Public License along with this program. If not, see http://www.mozilla.org/MPL/
 */

package org.openlmis.vaccine.service;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openlmis.db.categories.UnitTests;
import org.openlmis.vaccine.domain.config.VaccineIvdTabVisibility;
import org.openlmis.vaccine.repository.VaccineIvdTabVisibilityRepository;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;


@Category(UnitTests.class)
@RunWith(MockitoJUnitRunner.class)
public class VaccineIvdTabVisibilityServiceTest {

  @Mock
  VaccineIvdTabVisibilityRepository repository;

  @InjectMocks
  VaccineIvdTabVisibilityService service;

  @Test
  public void shouldGetAllPossibleVisibilityForProgram() throws Exception {
    List<VaccineIvdTabVisibility> list = asList(new VaccineIvdTabVisibility());

    when(repository.getVisibilityForProgram(2L)).thenReturn(null);
    when(repository.getAllVisibilityConfiguration()).thenReturn(list);

    List<VaccineIvdTabVisibility> result = service.getVisibilityForProgram(2L);
    assertThat(result, is(list));
  }

  @Test
  public void shouldGetSavedVisibilityForProgram() throws Exception {
    List<VaccineIvdTabVisibility> list = asList(new VaccineIvdTabVisibility());

    when(repository.getVisibilityForProgram(2L)).thenReturn(list);
    when(repository.getAllVisibilityConfiguration()).thenReturn(null);

    List<VaccineIvdTabVisibility> result = service.getVisibilityForProgram(2L);
    assertThat(result, is(list));

    verify(repository, never()).getAllVisibilityConfiguration();
  }

  @Test
  public void shouldSave() throws Exception {
    VaccineIvdTabVisibility visibility = new VaccineIvdTabVisibility();
    VaccineIvdTabVisibility visibility1 = new VaccineIvdTabVisibility();
    visibility.setId(23L);
    List<VaccineIvdTabVisibility> list = asList(visibility, visibility1);

    service.save(list);
    verify(repository).insert(visibility1);
    verify(repository).update(visibility);
  }
}