import {Button, Col, ListGroup, ModalBody, ModalDialog, ModalFooter, ModalTitle, Row} from 'react-bootstrap'
import ModalHeader from 'react-bootstrap/ModalHeader'
import PropTypes from 'prop-types'
import {useDispatch} from 'react-redux'
import {hideDialog} from '../../redux/slice/currentDialogSlice'
import {useState} from 'react'
import {BsImage} from 'react-icons/bs'

const DownloadDialog = ({image}) => {
  const dispatch = useDispatch()
  const [imageSize, setImageSize] = useState('NORMAL')
  const [sepia, setSepia] = useState(false)

  const download = () => {
    console.log(image)
    console.log(imageSize)
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
                                data-selected={imageSize === 'NORMAL'}
                                onClick={() => setImageSize('NORMAL')}>
                  <BsImage style="1.3em"/>
                </ListGroup.Item>
                <ListGroup.Item className="switcher-item text-center"
                                data-selected={imageSize === 'SMALL'}
                                onClick={() => setImageSize('SMALL')}>
                  <BsImage style="1em"/>
                </ListGroup.Item>
              </ListGroup>
            </Col>
          </Row>
          <Row>
            <Col>Filter:</Col>
            <Col><input type="checkbox" onChange={() => setSepia(!sepia)} checked={sepia}/>Sepia</Col>
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
