import {Button, Col, ListGroup, ModalBody, ModalDialog, ModalFooter, ModalTitle, Row} from 'react-bootstrap'
import ModalHeader from 'react-bootstrap/ModalHeader'
import PropTypes from 'prop-types'
import {useDispatch} from 'react-redux'
import {hideDialog} from '../../redux/slice/currentDialogSlice'
import {useState} from 'react'
import {BsImage} from 'react-icons/bs'
import {saveAs} from 'file-saver'
import {baseUrl} from '../../redux/api/baseApi'

const DownloadDialog = ({image}) => {
  const dispatch = useDispatch()
  const [imageSize, setImageSize] = useState('BIG')
  const [filter, setFilter] = useState(null)

  const download = () => {
    const url = `${baseUrl}/photo/${image.path}?size=${imageSize}` + (!filter ? '' : `&filter=${filter}`)
    saveAs(url, image.title + image.path.substring(image.path.lastIndexOf('.')))
    dispatch(hideDialog(null))
  }

  const cancel = () => {
    dispatch(hideDialog(null))
  }

  return (
    <div className="position-fixed top-0 bottom-0 w-100 dialog-box">
      <ModalDialog centered={true}>
        <ModalHeader>
          <ModalTitle>Download image</ModalTitle>
        </ModalHeader>
        <ModalBody>
          <Row>
            <Col>Image size:</Col>
            <Col>
              <ListGroup horizontal>
                <ListGroup.Item className="switcher-item text-center"
                                data-selected={imageSize === 'BIG'}
                                onClick={() => setImageSize('BIG')}>
                  <BsImage style={{fontSize: "1.3em"}}/>
                </ListGroup.Item>
                <ListGroup.Item className="switcher-item text-center"
                                data-selected={imageSize === 'SMALL'}
                                onClick={() => setImageSize('SMALL')}>
                  <BsImage style={{fontSize: "1em"}}/>
                </ListGroup.Item>
              </ListGroup>
            </Col>
          </Row>
          <Row>
            <Col>Filter:</Col>
            <Col><input type="radio" onChange={() => setFilter(null)} checked={filter === null}/>None</Col>
            <Col><input type="radio" onChange={() => setFilter('GREYSCALE')} checked={filter === 'GREYSCALE'}/>Grayscale</Col>
            <Col><input type="radio" onChange={() => setFilter('SEPIA')} checked={filter === 'SEPIA'}/>Invert</Col>
            <Col><input type="radio" onChange={() => setFilter('INVERT')} checked={filter === 'INVERT'}/>Sepia</Col>
          </Row>
        </ModalBody>
        <ModalFooter>
          <Button className="button-dark" onClick={cancel}>Cancel</Button>
          <Button className="button-dark" onClick={download}>Download</Button>
        </ModalFooter>
      </ModalDialog>
    </div>
  )
}

DownloadDialog.propTypes = {
  image: PropTypes.object.isRequired
}

export default DownloadDialog
