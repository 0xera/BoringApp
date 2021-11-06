package awesomeProject

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
)

func RunServer(port string) error {
	r := gin.Default()

	r.GET("/", func(c *gin.Context) {
		c.String(http.StatusOK, "from server")
	})
	err := r.Run(":" + port)
	if err != nil {
		return err
	}
	return nil
}

func GetString() string {
	return "Hello, world2"
}

func Sum(one, two int) int {
	return one + two
}

func Exception() error {
	return fmt.Errorf("")
}

func RunMillionGoroutines(d func(int2 int)) {
	for i := 0; i < 1_000_000; i++ {
		go func(i int) {
			d(i)
		}(i)
	}

}

