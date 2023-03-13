<%-- 
    Document   : index
    Created on : Mar 11, 2023, 7:37:18 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<main>
    <div class="container-fluid px-4">
        <h1 class="mt-4">Dashboard</h1>
        <ol class="breadcrumb mb-4">
            <li class="breadcrumb-item active">Dashboard</li>
        </ol>
        <div class="row">
            <div class="col-xl-4 col-md-6">
                <div class="card bg-primary text-white mb-4">
                    <div class="card-body">Account: ${accListSize} accounts.</div>
                    <div class="card-footer d-flex align-items-center justify-content-between">
                        <a class="small text-white stretched-link" href="<c:url value ="/admin_account/index.do" />">View Details</a>
                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                    </div>
                </div>
            </div>
            <div class="col-xl-4 col-md-6">
                <div class="card bg-warning text-white mb-4">
                    <div class="card-body">Product: ${proListSize} products.</div>
                    <div class="card-footer d-flex align-items-center justify-content-between">
                        <a class="small text-white stretched-link" href="<c:url value ="/admin_product/index.do" />">View Details</a>
                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                    </div>
                </div>
            </div>
            <div class="col-xl-4 col-md-6">
                <div class="card bg-success text-white mb-4">
                    <div class="card-body">Order: ${orderHeaderListSize} orders.</div>
                    <div class="card-footer d-flex align-items-center justify-content-between">
                        <a class="small text-white stretched-link" href="<c:url value ="/admin_order/index.do" />">View Details</a>
                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xl-12">
                <div class="card mb-4">
                    <div class="card-header">
                        <div class="datatable-top"> 
                            <div class="datatable-dropdown">
                                <label>
                                    Data of year:
                                    <select class="datatable-selector" id="yearOp">
                                        <option value="">Overall</option>
                                        <option value="">2020</option>
                                        <option value="">2021</option>
                                        <option value="">2022</option>
                                        <option value="">2023</option>
                                        <option value="">2024</option>
                                    </select>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-6">
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fa-solid fa-chart-pie"></i>
                        Percentages of categories purchased 
                    </div>
                    <div class="card-body"><canvas id="myPieChart" width="100%" height="50"></canvas></div>
                </div>
            </div>
            <div class="col-xl-6">
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fa-solid fa-sack-dollar"></i>
                        Revenue
                    </div>
                    <div class="card-body"><canvas id="myBarChart" width="100%" height="50"></canvas></div>
                </div>
            </div>
        </div>
</main>

<script src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" />" crossorigin="anonymous"></script>
<script>
    const labelsOverallBar = ["2020", "2021", "2022", "2023", "2024"];
    const labelsEachYearBar = ["January", "February", "March", "April", "May", "June", "July"
                , "August", "September", "October", "November", "December"];

    const under5000BG = "rgba(255, 99, 132, 0.2)";
    const under5000BD = "rgb(255, 99, 132)";
    const under10000BG = "rgba(255, 205, 86, 0.2)";
    const under10000BD = "rgb(255, 205, 86)";
    const over10000BG = "rgba(75, 192, 192, 0.2)";
    const over10000BD = "rgb(75, 192, 192)";

    const listBar2020 = ${listBar2020};
    const listBar2021 = ${listBar2021};
    const listBar2022 = ${listBar2022};
    const listBar2023 = ${listBar2023};
    const listBar2024 = ${listBar2024};
    const overallBarList = ${overallBarList};

    const listPie2020 = ${listPie2020};
    const listPie2021 = ${listPie2021};
    const listPie2022 = ${listPie2022};
    const listPie2023 = ${listPie2023};
    const listPie2024 = ${listPie2024};
    const overallPieList = ${overallPieList};
    
    var bgBar = [];
    var bdBar = [];

    var ctx = document.getElementById("myBarChart");

    for (let i = 0; i < 4; i++) {
        if (overallBarList[i] < 5000) {
            bgBar.push(under5000BG);
            bdBar.push(under5000BD);
        } else if (overallBarList[i] < 10000) {
            bgBar.push(under10000BG);
            bdBar.push(under10000BD);
        } else {
            bgBar.push(over10000BG);
            bdBar.push(over10000BD);
        }
    }

    //Bar Chart
    var myBarChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labelsOverallBar,
            datasets: [{
                    label: "$",
                    data: overallBarList,
                    backgroundColor: bgBar,
                    borderColor: bdBar,
                    borderWidth: 1,
                }],
        },
        options: {
            scales: {
                xAxes: [{
                        gridLines: {
                            display: false
                        },
                        ticks: {
                            maxTicksLimit: 12
                        }
                    }],
                yAxes: [{
                        ticks: {
                            min: 0,
                            max: 20000,
                            maxTicksLimit: 10
                        },
                        gridLines: {
                            display: true
                        }
                    }],
            },
            legend: {
                display: false
            }
        }
    });

    // Pie Chart 
    var ctx1 = document.getElementById("myPieChart");
    var myPieChart = new Chart(ctx1, {
        type: 'pie',
        data: {
            labels: ["Automatic", "Battery", "SmartWatch", "Sport"],
            datasets: [{
                    data: overallPieList,
                    backgroundColor: [
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(255, 99, 132, 0.2)'
                    ],
                    borderColor: [
                        'rgb(54, 162, 235)',
                        'rgb(255, 159, 64)',
                        'rgb(75, 192, 192)',
                        'rgb(255, 99, 132)'
                    ],
                    borderWidth: 1
                }],
        },
    });

    const  yearOp = document.getElementById("yearOp");
    yearOp.addEventListener('change', showData);

    function showData() {
        const yearOpText = yearOp.options[yearOp.selectedIndex].text;
        var labelsOfBarChart, dataOfBarChart;
        var dataOfPieChart;
        
        if (yearOpText == "Overall") {
            bgBar = [];
            bdBar = [];
            labelsOfBarChart = labelsOverallBar;
            dataOfPieChart = ${overallPieList};
            dataOfBarChart = ${overallBarList};
            for (let i = 0; i < 4; i++) {
                if (overallBarList[i] < 5000) {
                    bgBar.push(under5000BG);
                    bdBar.push(under5000BD);
                } else if (overallBarList[i] < 10000) {
                    bgBar.push(under10000BG);
                    bdBar.push(under10000BD);
                } else {
                    bgBar.push(over10000BG);
                    bdBar.push(over10000BD);
                }
            }
        } else if (yearOpText == "2020") {
            bgBar = [];
            bdBar = [];
            labelsOfBarChart = labelsEachYearBar;
            dataOfBarChart = ${listBar2020};
            dataOfPieChart = ${listPie2020};
            for (let i = 0; i < 12; i++) {
                if (listBar2020[i] < 5000) {
                    bgBar.push(under5000BG);
                    bdBar.push(under5000BD);
                } else if (listBar2020[i] < 10000) {
                    bgBar.push(under10000BG);
                    bdBar.push(under10000BD);
                } else {
                    bgBar.push(over10000BG);
                    bdBar.push(over10000BD);
                }
            }
        } else if (yearOpText == "2021") {
            bgBar = [];
            bdBar = [];
            labelsOfBarChart = labelsEachYearBar;
            dataOfBarChart = ${listBar2021};
            dataOfPieChart = ${listPie2021};
            console.log(${listPie2021});
            for (let i = 0; i < 12; i++) {
                if (listBar2021[i] < 5000) {
                    bgBar.push(under5000BG);
                    bdBar.push(under5000BD);
                } else if (listBar2021[i] < 10000) {
                    bgBar.push(under10000BG);
                    bdBar.push(under10000BD);
                } else {
                    bgBar.push(over10000BG);
                    bdBar.push(over10000BD);
                }
            }
        } else if (yearOpText == "2022") {
            bgBar = [];
            bdBar = [];
            labelsOfBarChart = labelsEachYearBar;
            dataOfBarChart = ${listBar2022};
            dataOfPieChart = ${listPie2022};
            for (let i = 0; i < 12; i++) {
                if (listBar2022[i] < 5000) {
                    bgBar.push(under5000BG);
                    bdBar.push(under5000BD);
                } else if (listBar2022[i] < 10000) {
                    bgBar.push(under10000BG);
                    bdBar.push(under10000BD);
                } else {
                    bgBar.push(over10000BG);
                    bdBar.push(over10000BD);
                }
            }
        } else if (yearOpText == "2023") {
            bgBar = [];
            bdBar = [];
            labelsOfBarChart = labelsEachYearBar;
            dataOfBarChart = ${listBar2023};
            dataOfPieChart = ${listPie2023};
            for (let i = 0; i < 12; i++) {
                if (listBar2023[i] < 5000) {
                    bgBar.push(under5000BG);
                    bdBar.push(under5000BD);
                } else if (listBar2023[i] < 10000) {
                    bgBar.push(under10000BG);
                    bdBar.push(under10000BD);
                } else {
                    bgBar.push(over10000BG);
                    bdBar.push(over10000BD);
                }
            }
        } else if (yearOpText == "2024") {
            bgBar = [];
            bdBar = [];
            labelsOfBarChart = labelsEachYearBar;
            dataOfBarChart = ${listBar2024};
            dataOfPieChart = ${listPie2024};
            for (let i = 0; i < 12; i++) {
                if (listBar2024[i] < 5000) {
                    bgBar.push(under5000BG);
                    bdBar.push(under5000BD);
                } else if (listBar2024[i] < 10000) {
                    bgBar.push(under10000BG);
                    bdBar.push(under10000BD);
                } else {
                    bgBar.push(over10000BG);
                    bdBar.push(over10000BD);
                }
            }
        }

        myBarChart.data.labels = labelsOfBarChart;
        myBarChart.data.datasets[0].data = dataOfBarChart;
        myBarChart.data.datasets[0].backgroundColor = bgBar;
        myBarChart.data.datasets[0].borderColor = bdBar;
        myBarChart.update();

        myPieChart.data.datasets[0].data = dataOfPieChart;
        myPieChart.update();
    }


</script>
