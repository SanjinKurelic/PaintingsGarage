import Header from './components/header/Header'
import ImageGallery from './components/image/ImageGallery'
import 'bootstrap/dist/css/bootstrap.min.css'
import {Container} from 'react-bootstrap'
import {useState} from 'react'
import Image from './components/image/Image'
import {useGetLatestImagesQuery} from './redux/api/searchApi'
import Footer from './components/footer/Footer'

function App() {
  const {data, isSuccess} = useGetLatestImagesQuery()
  const [selectedImage, setSelectedImage] = useState(null)

  return (
    <Container>
      <Header/>
      {isSuccess && <ImageGallery images={data} setSelectedImage={setSelectedImage}/>}
      {selectedImage && <Image image={selectedImage} setSelectedImage={setSelectedImage}/>}
      <Footer/>
    </Container>
  )
}

export default App
