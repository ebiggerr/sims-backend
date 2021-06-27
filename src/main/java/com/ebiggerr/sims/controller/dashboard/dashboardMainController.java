/*
 * Copyright (c) 2021 EbiggerR
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ebiggerr.sims.controller.dashboard;

import com.ebiggerr.sims.domain.response.API_Response;
import com.ebiggerr.sims.service.dashboard.dashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class dashboardMainController {

    private final dashboardService dashboardService;

    public dashboardMainController(dashboardService dashboardService){
        this.dashboardService=dashboardService;
    }

    /**
     * <h1> Dashboard </h1>
     *
     * Analytics information for dashboard module
     *
     * @return Information wrapped in dashboard object
     */
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping(path = "/analytics/all")
    public API_Response getAllAnalytics(){

        return new API_Response().Success( dashboardService.getAnalytics() );
    }


    /*@GetMapping(path = "/analytics/inventory_composition")
    public API_Response getInventoryComposition(){

        return new API_Response().Success();
    }

    @GetMapping(path = "/analytics/inventory_statistics")
    public API_Response getInventoryStatistics(){

        return new API_Response().Success();
    }*/

}
