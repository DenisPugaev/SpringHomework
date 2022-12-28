angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/app/api/v1';





    $scope.loadProducts = function (pageIndex=1) {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                page: pageIndex,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                name_part: $scope.filter ? $scope.filter.name_part : null
            }
        }).then(function (response) {

            $scope.ProductsList = response.data.content;
            $scope.ProductsList.sort(function (a, b) {
                return parseFloat(a.id) - parseFloat(b.id);
            }); // сортировка по ID
            // console.log(ProductsList)
        });
    }



    $scope.resetForm = function() {
            $scope.filter.min_price = null;
            $scope.filter.max_price = null;
            $scope.filter.name_part = null;
          $scope.loadProducts();
    };

    $scope.deleteProduct = function (productId) {
        console.log('Click deleteProduct', productId);
        $http.delete(contextPath + '/products/' + productId)
            .then(function (response) {
                $scope.loadProducts();
            });
    }


    $scope.changePrice = function (id, delta) {
        console.log('Click changePrice!', id);
        $http({
            url: contextPath + '/products/change_price',
            method: 'PUT',
            params: {
                id: id,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.addProduct = function () {
        console.log('Click addProduct!', $scope.newProduct);
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct.name = null;
                $scope.newProduct.price = null;
                $scope.newProduct.manufacturer = null;
                $scope.loadProducts();
            });
    }


    $scope.loadProducts();

});
